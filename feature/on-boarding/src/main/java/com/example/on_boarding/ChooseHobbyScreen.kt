@file:OptIn(ExperimentalMaterial3Api::class)

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.on_boarding.R
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

@Composable
fun ChooseHobbyScreen() {
    val hobbies = remember { mutableStateListOf("Yoga", "Running", "Swimming") }

    Scaffold(
        topBar = { TopBar() },
        bottomBar = { CustomBottomBar() } // 변경된 부분
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            hobbies.forEachIndexed { index, hobby ->
                SwipeableCard(
                    hobby = hobby,
                    index,
                    onSwipe = { if (hobbies.size > 1) hobbies.removeAt(index) }
                )
            }
        }
    }
}

@Composable
fun TopBar() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "Choose a hobby",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        },
        actions = {
            IconButton(onClick = { /* TODO */ }) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile Icon",
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    )
}

@Composable
fun SwipeableCard(hobby: String,index:Int ,onSwipe: () -> Unit) {
    val offsetX = remember { Animatable(0f) }
    val offsetY = remember { Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxWidth(1f)
            .offset { IntOffset(offsetX.value.roundToInt(), offsetY.value.roundToInt()) }
            .graphicsLayer {
                rotationZ = offsetX.value / 40 // Adjust the divisor to control the tilt degree
                alpha = 1 - (offsetX.value.absoluteValue / 1000f).coerceIn(0f, 1f)
            }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragEnd = {
                        coroutineScope.launch {
                            if (offsetX.value.absoluteValue > 300) {
                                val targetX = if (offsetX.value > 0) 1000f else -1000f
                                val targetY = if (offsetX.value > 0) 500f else 500f
                                offsetX.animateTo(
                                    targetValue = targetX,
                                    animationSpec = tween(durationMillis = 300)
                                )
                                offsetY.animateTo(
                                    targetValue = targetY,
                                    animationSpec = tween(durationMillis = 300)
                                )
                                onSwipe()
                                offsetX.snapTo(0f)
                                offsetY.snapTo(0f)
                            } else {
                                launch {
                                    offsetX.animateTo(0f, tween(300))
                                }
                                launch {
                                    offsetY.animateTo(0f, tween(300))
                                }
                            }
                        }
                    }
                ) { change, dragAmount ->
                    change.consume()
                    coroutineScope.launch {
                        offsetX.snapTo(offsetX.value + dragAmount.x)
                        // Adjust this part to ensure the card moves smoothly and doesn't go off-screen
                        if (offsetX.value > 0) {
                            offsetY.snapTo(offsetY.value + (dragAmount.x / 2).coerceAtLeast(-offsetY.value))
                        } else {
                            offsetY.snapTo(offsetY.value + (-dragAmount.x / 2).coerceAtLeast(-offsetY.value))
                        }
                    }
                }
            },
        contentAlignment = Alignment.Center
    ) {
        HobbyCardContent(hobby = hobby,index)
    }
}

@Composable
fun HobbyCardContent(hobby: String,index: Int) {
    val context = LocalContext.current
    val resourceId = remember(index) {
        context.resources.getIdentifier("number$index", "drawable", context.packageName)
    }
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = resourceId),
                contentDescription = "number${index}",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop
            )

            Text(
                text = hobby,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                IconButton(
                    onClick = { /* TODO */ },
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(Color.Gray)
                ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Dislike",
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }

                IconButton(
                    onClick = { /* TODO */ },
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(Color.Red)
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Like",
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun CustomBottomBar() {
    var selectedItem by remember { mutableStateOf(-1) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)) // 둥근 모서리 적용
            .background(Color.White)
            .padding(vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            BottomBarItem(
                icon = Icons.Default.Favorite,
                contentDescription = "Heart",
                isSelected = selectedItem == 0,
                onClick = { selectedItem = 0 }
            )
            BottomBarItem(
                icon = Icons.Default.Call,
                contentDescription = "Phone",
                isSelected = selectedItem == 1,
                onClick = { selectedItem = 1 }
            )
            BottomBarItem(
                icon = Icons.Default.Settings,
                contentDescription = "Settings",
                isSelected = selectedItem == 2,
                onClick = { selectedItem = 2 }
            )
        }
    }
}

@Composable
fun BottomBarItem(
    icon: ImageVector,
    contentDescription: String?,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val scale by animateFloatAsState(targetValue = if (isSelected) 1.5f else 1f, label = "")

    IconButton(onClick = onClick) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            modifier = Modifier
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                },
            tint = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewChooseHobbyScreen() {
    ChooseHobbyScreen()
}
