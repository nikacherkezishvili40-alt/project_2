package com.example.myapplication

import android.app.DatePickerDialog
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Calendar

private val Red = Color.Red
private val Gray = Color.LightGray
private val Green = Color.Green
private val SurfaceWhite   = Color(0xFFFFFFFF)
private val BackgroundGray = Color(0xFFF3F4F6)
private val TextPrimary    = Color(0xFF111827)
private val TextHint       = Color(0xFF9CA3AF)

@Composable
fun StudentFormScreen() {
    val context = LocalContext.current

    var nameState      by remember { mutableStateOf("") }
    var emailState     by remember { mutableStateOf("") }
    var dateState      by remember { mutableStateOf("") }
    var selectedOption by remember { mutableStateOf("") }
    var isAgreed       by remember { mutableStateOf(false) }

    val favoriteOptions = listOf("Python", "Java", "Kotlin","JavaScript")

    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, day ->
            dateState = "%02d/%02d/%04d".format(day, month + 1, year)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    fun onSubmit() {
        when {
            nameState.isBlank() || emailState.isBlank() || dateState.isBlank() ->
                Toast.makeText(context, "შეავსეთ ყველა ველი!", Toast.LENGTH_SHORT).show()
            selectedOption.isEmpty() ->
                Toast.makeText(context, "შეავსეთ ყველა ველი!", Toast.LENGTH_SHORT).show()
            !isAgreed ->
                Toast.makeText(context, "შეავსეთ ყველა ველი!", Toast.LENGTH_SHORT).show()
            else ->
                Toast.makeText(context, "მონაცემები გაიგზავნა!", Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGray)
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Red)
                .padding(horizontal = 20.dp, vertical = 20.dp)
        ) {
            Column {
                Text("სტუდენტის ფორმა", color = SurfaceWhite, fontSize = 30.sp, fontWeight = FontWeight.Bold)
                Text("შეავსეთ თქვენი მონაცემები", color = SurfaceWhite.copy(alpha = 0.85f), fontSize = 13.sp)
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Gray),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                Text("თქვენი სახელი", fontSize = 16.sp, fontWeight = FontWeight.Medium, color = Red)
                OutlinedTextField(
                    value = nameState,
                    onValueChange = { nameState = it },
                    placeholder = { Text("შეიყვანეთ თქვენი სახელი",fontSize = 14.sp, color = TextHint) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                )

                Text("დაბადების თარიღი", fontSize = 16.sp, fontWeight = FontWeight.Medium, color = Red)
                OutlinedTextField(
                    value = dateState,
                    onValueChange = {},
                    placeholder = { Text("დდ/თთ/წწწწ",fontSize = 14.sp, color = TextHint) },
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true,
                    singleLine = true,
                    trailingIcon = {
                        IconButton(onClick = { datePickerDialog.show() }) {
                            Icon(
                                Icons.Default.CalendarMonth,
                                contentDescription = "აირჩიეთ თარიღი",
                                tint = Red
                            )
                        }
                    }
                )

                Text("ელ.ფოსტა", fontSize = 16.sp, fontWeight = FontWeight.Medium, color = Red)
                OutlinedTextField(
                    value = emailState,
                    onValueChange = { emailState = it },
                    placeholder = { Text("თქვენი.ელ.ფოსტა@example.com",fontSize = 14.sp, color = TextHint) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                )

                HorizontalDivider(color = Color(0xFFE5E7EB))

                Text("საყვარელი პროგრამირების ენა", fontSize = 13.sp, fontWeight = FontWeight.Medium, color = Red)
                favoriteOptions.forEach { option ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = selectedOption == option,
                            onClick = { selectedOption = option },
                            colors = RadioButtonDefaults.colors(selectedColor = Green)
                        )
                        Text(option, fontSize = 15.sp, color = TextPrimary)
                    }
                }

                HorizontalDivider(color = Color(0xFFE5E7EB))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "ვეთანხმები წესებს და პირობებს",
                        fontSize = 14.sp,
                        color = TextPrimary,
                        modifier = Modifier.weight(1f)
                    )
                    Switch(
                        checked = isAgreed,
                        onCheckedChange = { isAgreed = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = SurfaceWhite,
                            checkedTrackColor = Red
                        )
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = { onSubmit() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(6.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Red)
                ) {
                    Text("Submit", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = SurfaceWhite)
                }
            }
        }
    }
}