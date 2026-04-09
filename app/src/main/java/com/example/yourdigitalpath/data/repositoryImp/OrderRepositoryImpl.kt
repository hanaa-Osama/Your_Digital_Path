package com.example.yourdigitalpath.data.repositoryImp

import com.example.yourdigitalpath.domain.model.OrderTrackingDetail
import com.example.yourdigitalpath.domain.repository.OrderRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class OrderRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : OrderRepository {

    override suspend fun addNewOrder(order: OrderTrackingDetail) {
        try {
            // بنرفع الـ object كامل والفايربيز هيفهمه لو الـ fields مطابقة
            firestore.collection("orders")
                .document(order.orderId)
                .set(order)
                .await()
        } catch (e: Exception) {
            throw e
        }
    }
}

//fun addNewOrder(serviceName: String) {
//    val db = FirebaseFirestore.getInstance()
//
//    // 1. تجهيز تاريخ النهاردة
//    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
//    val currentDate = sdf.format(Date())
//
//    // 2. تجهيز أول خطوة في التتبع (زي ما في الصورة)
//    val firstStep = hashMapOf(
//        "id" to 1,
//        "status" to "completed",
//        "title" to "تم استلام الطلب",
//        "timestamp" to SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())
//    )
//
//    // 3. تجميع بيانات الطلب كاملة
//    val orderId = "REQ-${System.currentTimeMillis().toString().takeLast(5)}" // رقم عشوائي للطلب
//    val orderData = hashMapOf(
//        "orderId" to orderId,
//        "date" to currentDate,
//        "serviceType" to serviceName, // هنا هتباصي "هوية رقمية"
//        "steps" to listOf(firstStep) // بنضيف أول خطوة في الـ Array
//    )
//
//    // 4. الرفع للفايربيز في كولكشن orders
//    db.collection("orders")
//        .document(orderId) // ممكن تستخدمي ID تلقائي أو الـ orderId اللي عملناه
//        .set(orderData)
//        .addOnSuccessListener {
//            // هنا ممكن تطلعي Toast إن الطلب تم بنجاح
//            println("Order added successfully!")
//        }
//        .addOnFailureListener { e ->
//            println("Error adding order: $e")
//        }
//}