package com.example.psychologycareapps

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
                if (data[i].answer == 0) {
                    Toast.makeText(this, "Mohon isi semua pertanyaan", Toast.LENGTH_SHORT).show()
                    allowCalculate = false
                    break
                }
            }

            //limit submit jawaban dengan menghitung jumlah data yang sudah ada di firebase
            val uid = FirebaseAuth.getInstance().currentUser?.uid
            val time = Calendar.getInstance().time
            val formatter = SimpleDateFormat("MMMM-yyyy")
            val current = formatter.format(time)
            var counter = 0
            val ref = FirebaseFirestore.getInstance().collection("Test Psikologi").document(uid!!)
            ref.collection("Depresi").document("value").collection(current).get()
                .addOnSuccessListener {documents ->
                    counter = documents.count()

                    if (counter >= 4) {
                        allowCalculate = true
                        Toast.makeText(applicationContext, "Anda sudah mencapai batas pengisian", Toast.LENGTH_SHORT).show()
                    } else {
                        calculate(data, uid, current)
                    }
                }
                .addOnFailureListener {exception ->
                    Log.d(TAG, "get failed with", exception)
                }

            finish()

        }

    }

    //the logic
    private fun calculate(data: ArrayList<ModelDass>, uid: String, current: String) {
        var depresi = 0
        var stress = 0
        var anxiety = 0

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
        var convertDepresi : Float = (depresi / 20).toDouble().toFloat()
        var convertStress : Float = (stress / 20).toDouble().toFloat()
        var convertAnxiety : Float = (anxiety / 20).toDouble().toFloat()

        var saveDepresi = mapOf<String, Int>("value" to depresi)
        var savestress = mapOf<String, Int>("value" to stress)
        var saveAnxiety = mapOf<String, Int>("value" to anxiety)

        //upload firebase
        //save data depresi
        ref.collection("Depresi").document("value").collection(current).document()
            .set(saveDepresi).addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(applicationContext, "Data ada berhasil disimpan", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(applicationContext, "Data anda gagal disimpan coba lagi beberasaat", Toast.LENGTH_SHORT).show()
                }
            }

        //save data stress
        ref.collection("Stress").document("value").collection(current).document()
            .set(savestress).addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(applicationContext, "Data ada berhasil disimpan", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(applicationContext, "Data anda gagal disimpan coba lagi beberasaat", Toast.LENGTH_SHORT).show()
                }
            }

        //save data anxiety
        ref.collection("Anxiety").document("value").collection(current).document()
            .set(saveAnxiety).addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(applicationContext, "Data ada berhasil disimpan", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(applicationContext, "Data anda gagal disimpan coba lagi beberasaat", Toast.LENGTH_SHORT).show()
                }
            }

    }

    val ArrayStatement : ArrayList<ModelDass>get() {
        val arraystatement = ArrayList<ModelDass>()

        val statement1 = ModelDass()
        statement1.id = "1"
        statement1.type = "depresi"
        statement1.statement = "1. Saya merasa sedih dan murung."
        arraystatement.add(statement1)
        val statement2 = ModelDass()
        statement2.id = "2"
        statement2.type = "depresi"
        statement2.statement = "2. Saya merasa tidak menarik atau kehilangan minat pada hal-hal yang biasanya saya nikmati."
        arraystatement.add(statement2)
        val statement3 = ModelDass()
        statement3.id = "3"
        statement3.type = "depresi"
        statement3.statement = "3. Saya merasa tidak berharga atau tidak berguna."
        arraystatement.add(statement3)
        val statement4 = ModelDass()
        statement4.id = "4"
        statement4.type = "depresi"
        statement4.statement = "4. Saya merasa tidak bersemangat atau tanpa harapan."
        arraystatement.add(statement4)
        val statement5 = ModelDass()
        statement5.id = "5"
        statement5.type = "depresi"
        statement5.statement = "5. Saya merasa cemas atau tertekan."
        arraystatement.add(statement5)
        val statement6 = ModelDass()
        statement6.id = "6"
        statement6.type = "anxiety"
        statement6.statement = "6. Saya merasa sedih dan murung."
        arraystatement.add(statement6)
        val statement7 = ModelDass()
        statement7.id = "7"
        statement7.type = "anxiety"
        statement7.statement = "7. Saya merasa tidak menarik atau kehilangan minat pada hal-hal yang biasanya saya nikmati."
        arraystatement.add(statement7)
        val statement8 = ModelDass()
        statement8.id = "8"
        statement8.type = "anxiety"
        statement8.statement = "8. Saya merasa tidak berharga atau tidak berguna."
        arraystatement.add(statement8)
        val statement9 = ModelDass()
        statement9.id = "9"
        statement9.type = "anxiety"
        statement9.statement = "9. Saya merasa tidak bersemangat atau tanpa harapan."
        arraystatement.add(statement9)
        val statement10 = ModelDass()
        statement10.id = "10"
        statement10.type = "anxiety"
        statement10.statement = "10. Saya merasa cemas atau tertekan."
        arraystatement.add(statement10)
        val statement11 = ModelDass()
        statement11.id = "11"
        statement11.type = "stress"
        statement11.statement = "11. Saya merasa sedih dan murung."
        arraystatement.add(statement11)
        val statement12 = ModelDass()
        statement12.id = "12"
        statement12.type = "stress"
        statement12.statement = "12. Saya merasa tidak menarik atau kehilangan minat pada hal-hal yang biasanya saya nikmati."
        arraystatement.add(statement12)
        val statement13 = ModelDass()
        statement13.id = "13"
        statement13.type = "stress"
        statement13.statement = "13. Saya merasa tidak berharga atau tidak berguna."
        arraystatement.add(statement13)
        val statement14 = ModelDass()
        statement14.id = "14"
        statement14.type = "stress"
        statement14.statement = "14. Saya merasa tidak bersemangat atau tanpa harapan."
        arraystatement.add(statement14)
        val statement15 = ModelDass()
        statement15.id = "15"
        statement15.type = "stress"
        statement15.statement = "15. Saya merasa cemas atau tertekan."
        arraystatement.add(statement15)

        return arraystatement
    }

    companion object{
        private const val TAG = "DassActivity"
    }

}