package com.example.psychologycareapps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.psychologycareapps.adapter.DassAdapter
import com.example.psychologycareapps.databinding.ActivityDassBinding
import com.example.psychologycareapps.model.ModelDass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.log

class DassActivity : AppCompatActivity() {

    lateinit var binding : ActivityDassBinding
    lateinit var rv_statement :RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDassBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val lmStatement = LinearLayoutManager(applicationContext)
        lmStatement.orientation = LinearLayoutManager.VERTICAL
        rv_statement = binding.rvStatemant

        val adapterStatement = DassAdapter(ArrayStatement, this)
        rv_statement.setHasFixedSize(true)
        rv_statement.layoutManager = lmStatement
        rv_statement.adapter = adapterStatement

        binding.btnSubmit.setOnClickListener {
            val data = adapterStatement.getSelectedItemData()
            var allowCalculate = true

            //validation apakah pernyataan sudah diisi semua?
            for (i in 1..data.size-1) {
                Log.d(DassActivity.TAG, "onCreate: q: " + data[i].statement + " a: " + data[i].answer)
                if (data[i].answer == -1) {
                    Toast.makeText(this, "Mohon isi semua pertanyaan", Toast.LENGTH_SHORT).show()
                    allowCalculate = false
                    break
                }
            }

            //limit submit jawaban dengan menghitung jumlah data yang sudah ada di firebase
            val uid = FirebaseAuth.getInstance().currentUser?.uid
            val time = Calendar.getInstance().time
            val formatter = SimpleDateFormat("MM-yyyy")
            val current = formatter.format(time)
            val ref = FirebaseFirestore.getInstance().collection("Tes Psikologi").document(uid!!)
            ref.collection("Depresi").document("value").collection(current).get()
                .addOnSuccessListener {documents ->
                    val counter = documents.count()
                    Log.d(TAG, "toatal item: ${counter}")

                    if (counter > 4) {
                        allowCalculate = false
                        Toast.makeText(applicationContext, "Anda sudah mencapai batas pengisian", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
                .addOnFailureListener {exception ->
                    Log.d(TAG, "get failed with", exception)
                }

            Log.d(TAG, "allow calculate: ${allowCalculate}")

            if (allowCalculate){
                calculate(data, uid, current)
            }

        }

    }

    //the logic
    private fun calculate(data: ArrayList<ModelDass>, uid: String, current: String) {
        var depresi = 0
        var stress = 0
        var anxiety = 0
        var time = Calendar.getInstance().time

        //total
        data.forEach {
            if (it.type == "depresi") {
                depresi += it.answer
            } else if (it.type == "stress") {
                stress += it.answer
            } else if (it.type == "anxiety") {
                anxiety += it.answer
            }
        }
        Log.d(TAG, "calculate: depresi: " + depresi)
        Log.d(TAG, "calculate: stress: " + stress)
        Log.d(TAG, "calculate: anxiety: " + anxiety)

        val ref = FirebaseFirestore.getInstance().collection("Tes Psikologi").document(uid)
//        var convertDepresi : Float = (depresi / 20).toDouble().toFloat()
//        var convertStress : Float = (stress / 20).toDouble().toFloat()
//        var convertAnxiety : Float = (anxiety / 20).toDouble().toFloat()

        var saveDepresi = mapOf<String, Int>("value" to depresi)
        var savestress = mapOf<String, Int>("value" to stress)
        var saveAnxiety = mapOf<String, Int>("value" to anxiety)

        //upload firebase
        //save data depresi
        ref.collection("Depresi").document("value").collection(current).document(time.toString())
            .set(saveDepresi).addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(applicationContext, "Data ada berhasil disimpan", Toast.LENGTH_SHORT).show()
                } else {
                    Log.d(TAG, "calculate: Data depresi gagal disimpan")
                }
            }

        //save data stress
        ref.collection("Stress").document("value").collection(current).document(time.toString())
            .set(savestress).addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.d(TAG, "calculate: Data stress berhasil disimpan")
                } else {
                    Log.d(TAG, "calculate: Data stess gagal disimpan")
                }
            }

        //save data anxiety
        ref.collection("Anxiety").document("value").collection(current).document(time.toString())
            .set(saveAnxiety).addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(applicationContext, "Data ada berhasil disimpan", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(applicationContext, "Data anda gagal disimpan coba lagi beberasaat", Toast.LENGTH_SHORT).show()
                }
            }

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()

    }

    val ArrayStatement : ArrayList<ModelDass>get() {
        val arraystatement = ArrayList<ModelDass>()

        val typeDepresi = "depresi"
        val typeAnxiety = "anxiety"
        val typeStress = "stress"

        val statement1 = ModelDass()
        statement1.id = "1"
        statement1.type = typeStress
        statement1.statement = "1. Saya marah tentang hal-hal kecil."
        arraystatement.add(statement1)
        val statement2 = ModelDass()
        statement2.id = "2"
        statement2.type = typeAnxiety
        statement2.statement = "2. Saya merasa pusing seperti mau pingsan."
        arraystatement.add(statement2)
        val statement3 = ModelDass()
        statement3.id = "3"
        statement3.type = typeDepresi
        statement3.statement = "3. Saya tidak menikmati apapun."
        arraystatement.add(statement3)
        val statement4 = ModelDass()
        statement4.id = "4"
        statement4.type = typeAnxiety
        statement4.statement = "4. Saya mengalami kesulitan bernapas (mis. pernapasan cepat), meskipun saya tidak berolahraga dan tidak sakit."
        arraystatement.add(statement4)

        val statement5 = ModelDass()
        statement5.id = "5"
        statement5.type = typeDepresi
        statement5.statement = "5. Aku benci hidupku."
        arraystatement.add(statement5)
        val statement6 = ModelDass()
        statement6.id = "6"
        statement6.type = typeStress
        statement6.statement = "6. Saya mendapati diri saya bereaksi berlebihan terhadap situasi."
        arraystatement.add(statement6)
        val statement7 = ModelDass()
        statement7.id = "7"
        statement7.type = typeAnxiety
        statement7.statement = "7. Tanganku terasa gemetar."
        arraystatement.add(statement7)
        val statement8 = ModelDass()
        statement8.id = "8"
        statement8.type = typeStress
        statement8.statement = "8. Saya menekankan tentang banyak hal."
        arraystatement.add(statement8)
        val statement9 = ModelDass()
        statement9.id = "9"
        statement9.type = typeAnxiety
        statement9.statement = "9. Saya merasa ketakutan."
        arraystatement.add(statement9)

        val statement10 = ModelDass()
        statement10.id = "10"
        statement10.type = typeDepresi
        statement10.statement = "10. Tidak ada hal baik yang bisa saya nantikan."
        arraystatement.add(statement10)
        val statement11 = ModelDass()
        statement11.id = "11"
        statement11.type = typeStress
            statement11.statement = "11. Saya mudah tersinggung."
        arraystatement.add(statement11)
        val statement12 = ModelDass()
        statement12.id = "12"
        statement12.type = typeStress
        statement12.statement = "12. Saya merasa sulit untuk bersantai."
        arraystatement.add(statement12)
        val statement13 = ModelDass()
        statement13.id = "13"
        statement13.type = typeDepresi
        statement13.statement = "13. Saya tidak bisa berhenti merasa sedih."
        arraystatement.add(statement13)
        val statement14 = ModelDass()
        statement14.id = "14"
        statement14.type = typeStress
        statement14.statement = "14. Saya kesal ketika orang mengganggu saya."
        arraystatement.add(statement14)

        val statement15 = ModelDass()
        statement15.id = "15"
        statement15.type = typeAnxiety
        statement15.statement = "15. Saya merasa seperti akan panik."
        arraystatement.add(statement15)
        val statement16 = ModelDass()
        statement16.id = "16"
        statement16.type = typeDepresi
        statement16.statement = "16. Aku membenci diriku sendiri."
        arraystatement.add(statement16)
        val statement17 = ModelDass()
        statement17.id = "17"
        statement17.type = typeDepresi
        statement17.statement = "17. Saya merasa seperti saya tidak baik."
        arraystatement.add(statement17)
        val statement18 = ModelDass()
        statement18.id = "18"
        statement18.type = typeStress
        statement18.statement = "18. Saya mudah kesal."
        arraystatement.add(statement18)
        val statement19 = ModelDass()
        statement19.id = "19"
        statement19.type = typeAnxiety
        statement19.statement = "19. Saya bisa merasakan jantung saya berdetak kencang, meskipun saya tidak melakukan olahraga berat."
        arraystatement.add(statement19)

        val statement20 = ModelDass()
        statement20.id = "20"
        statement20.type = typeAnxiety
        statement20.statement = "20. Saya merasa takut tanpa alasan yang jelas."
        arraystatement.add(statement20)
        val statement21 = ModelDass()
        statement21.id = "21"
        statement21.type = typeDepresi
        statement21.statement = "21. Saya merasa hidup itu mengerikan."
        arraystatement.add(statement21)

        return arraystatement
    }

    companion object{
        private const val TAG = "DassActivity"
    }

}