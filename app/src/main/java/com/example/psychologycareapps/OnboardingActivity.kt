package com.example.psychologycareapps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.psychologycareapps.ui.theme.*
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch


class OnboardingActivity : AppCompatActivity() {
    @OptIn(ExperimentalPagerApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OnboardingDemoTheme {
                Surface(modifier = Modifier.fillMaxSize()){
                    val items = ArrayList<OnboardingData>()
                    items.add(
                        OnboardingData(
                            R.drawable.meditiation,
//                            backgroundColor = Color(0xFFF3E6DD),
//                            mainColor = Color(0xFF6C3D37),
                            mainText = "Psychology Care",
                            subText = "Adalah sebuah aplikasi healt care yang membantu memonitoring kondisi psikologi pengguna setiap bulannya..."
                        )
                    )
                    items.add(
                        OnboardingData(
                            R.drawable.graphic,
//                            backgroundColor = Color(0xFFE4AF19),
//                            mainColor = ColorYellow,
                            mainText = "Mood Tracker",
                            subText = "Mood anda dalam setiap  minggunya dapat dicatat oleh sistem dengan mengajukan beberapa pertanyaan..."
                        )
                    )
                    items.add(
                        OnboardingData(
                            R.drawable.media_player,
//                            backgroundColor = Color(0xFF96E172),
//                            mainColor = ColorGreen,
                            mainText = "Fix Your Mood",
                            subText = "Apapun mood yang anda miliki sistem akan memberikan rekomendasi media dalam bentuk musik maupun vidio yang dapat membuat anda tenang..."
                        )
                    )

                    val pagerState = rememberPagerState(
                        pageCount = items.size,
                        initialOffscreenLimit = 2,
                        infiniteLoop = false,
                        initialPage = 0
                    )

                    OnboardingPager(
                        item = items,
                        pagerState = pagerState,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@OptIn(DelicateCoroutinesApi::class)
@ExperimentalPagerApi
@Composable
fun OnboardingPager(
    item: List<OnboardingData>,
    pagerState: PagerState,
    modifier: Modifier=Modifier
) {
    val context = LocalContext.current
    Box(modifier = modifier) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            HorizontalPager(state = pagerState) { page ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .padding(start = 20.dp, top = 80.dp, end = 20.dp, bottom = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    Image(
                        painter = painterResource(
                            id = item[page].image
                        ),
                        contentDescription = "",
                    )
                }
            }
        }

        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            Card(
               backgroundColor = Color.White,
               modifier = Modifier.fillMaxWidth()
                   .height(340.dp),
                elevation = 15.dp,
                shape = BottomCardShape.large
            ) {
                Box {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ){
                        PagerIndicator(items = item,currentPage = pagerState.currentPage)
                        Text(
                            text = item[pagerState.currentPage].mainText,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 20.dp, end = 30.dp),
                            color = Color(0xFF8EC3B0),
                            fontFamily = Poppins,
                            textAlign = TextAlign.Right,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold
                        )

                        Text(
                            text = item[pagerState.currentPage].subText,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 20.dp, start = 40.dp, end = 20.dp),
                            color = Color(0xFF3D3C42),
                            fontFamily = Poppins,
                            textAlign = TextAlign.Center,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.ExtraLight
                        )
                    }

                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(30.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            if(pagerState.currentPage != 2) {

                                TextButton(onClick = {
                                    context.startActivity(Intent(context, MainActivity::class.java))
                                }) {
                                    Text(
                                        text = "Skip Now",
                                        color = Color(0xFF8EC3B0),
                                        fontFamily = Poppins,
                                        textAlign = TextAlign.Right,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }

                                OutlinedButton(
                                    onClick = {
                                        GlobalScope.launch {
                                            pagerState.scrollToPage(
                                                pagerState.currentPage + 1,
                                                pageOffset = 0f
                                            )
                                        }
                                    },
                                    border = BorderStroke(
                                        14.dp,
                                        Color(0xFF8EC3B0)
                                    ),
                                    shape = RoundedCornerShape(50),
                                    colors = ButtonDefaults.outlinedButtonColors(
                                        contentColor = Color(0xFF8EC3B0)
                                    ),
                                    modifier = Modifier.size(65.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_right_arrow),
                                        contentDescription = "",
                                        modifier = Modifier.size(20.dp)
                                    )
                                }

                            } else {
                                Button(
                                    onClick = {
                                        context.startActivity(Intent(context, MainActivity::class.java))
                                              },
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = Color(0xFF9ED5C5)
                                    ),
                                    contentPadding = PaddingValues(vertical = 12.dp),
                                    elevation = ButtonDefaults.elevation(
                                        defaultElevation = 0.dp
                                    ),
                                    modifier = Modifier.fillMaxWidth()
                                    ) {
                                    Text(
                                        text = "Get Started",
                                        color = Color.White,
                                        fontSize = 16.sp
                                    )
                                }
                            }

                        }
                    }

                }
            }
        }

    }
}

@Composable
fun PagerIndicator(items: List<OnboardingData>, currentPage: Int) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.padding(top = 20.dp)
    ) {
        repeat(items.size){
            Indicator(
                isSelected = it == currentPage,
                color = items[it].mainColor
            )
        }
    }
}

@Composable
fun Indicator(isSelected:Boolean, color:Color) {
    val width = animateDpAsState(targetValue = if (isSelected) 40.dp else 10.dp)

    Box(
        modifier = Modifier
            .padding(4.dp)
            .height(8.dp)
            .width(width.value)
            .clip(CircleShape)
            .background(
                if (isSelected) color else Color.Gray.copy(alpha = 0.5f)
            )
    )
}
