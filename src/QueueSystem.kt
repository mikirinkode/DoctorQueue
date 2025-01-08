import javax.print.Doc

class QueueSystem {
    private var doctorQueues: ArrayList<DoctorQueue> = arrayListOf()
    private var doctorList: ArrayList<Doctor> = arrayListOf()

    fun initQueueSystem(doctorList: List<Doctor>) {
        this.doctorList.addAll(doctorList)
        doctorList.forEach { doctor ->
            doctorQueues.add(
                DoctorQueue(
//                    doctor = doctor,
                    doctorId = doctor.id,
                    patientQueueList = arrayListOf(),
                    totalEstimatedTimeInMinutes = 0,
                    queueLength = 0,
                    consultationTimeInMinutes = doctor.consultationTimeInMinutes
                )
            )
        }
//        println("INIT QUEUE SYSTEM")
//        println()
//        printQueueInfo()
    }

    fun addPatient(patient: Patient) {
        enqueue(patient)
    }

    // function to add patient to a queue
    private fun enqueue(patient: Patient) {
//        println("ENQUEUE : ${patient.name}")
//        val selectedDoctorQueue = doctorQueues.minWithOrNull(
//            compareByDescending<DoctorQueue> { it.totalEstimatedTimeInMinutes }.thenBy { it.doctor.consultationTimeInMinutes }
//        )

        val selectedDoctorQueue = findDoctorQueue()

        val selectedDoctor: Doctor? = getDoctorById(selectedDoctorQueue.doctorId)

        if (selectedDoctor == null){
            println("NO DOCTOR FOUND")
            return
        }
        val position = selectedDoctorQueue.queueLength + 1
        val estimatedTime = if (position == 1)  0 else selectedDoctor.consultationTimeInMinutes * (position - 1)
//        println("SELECTED DOCTOR: ${selectedDoctor.name}")
//        println("Current queue length: ${selectedDoctorQueue.queueLength}")
//        println("Position: $position")
//        println("Estimated Time: $estimatedTime")
//        println()

        val patientQueueItem = PatientQueueItem(
            queuePosition = position,
//            patient = patient,
            patientId = patient.id,
//            doctor = selectedDoctor,
            doctorId = selectedDoctor.id,
            estimatedTimeInMinutes = estimatedTime,
        )

        selectedDoctorQueue.patientQueueList.add(patientQueueItem)

        val newDoctorQueue = selectedDoctorQueue.copy(
            patientQueueList = selectedDoctorQueue.patientQueueList,
            totalEstimatedTimeInMinutes = estimatedTime,
            queueLength = selectedDoctorQueue.queueLength + 1
        )

        doctorQueues.remove(selectedDoctorQueue)
        doctorQueues.add(newDoctorQueue)

        println("Patient ${patient.name} has been added to ${selectedDoctor.name}'s queue with position #${position} & estimated time: $estimatedTime minutes.")
        //        // determine the doctor and estimated time
//        if (queue.isEmpty()){
//            // if queue is empty
//            // rules:
//            // find doctor with the fastest average consultation time
//            // add the patient queue with the doctor
//            val doctorQueue = queue.minBy { it.doctor.consultationTimeInMinutes }
//            val doctor = doctorQueue.doctor
//
//            val patientQueueItem = PatientQueueItem(
//                queuePosition = queue.size + 1,
//                patient = patient,
//                doctor = doctor,
//                estimatedTimeInMinutes = doctor.consultationTimeInMinutes,
//            )
//
//            doctorQueue.patientQueueList.add(patientQueueItem)
//            val newDoctorQueue = doctorQueue.copy(
//                patientQueueList = doc,
//                totalEstimatedTimeInMinutes = doctorQueue.totalEstimatedTimeInMinutes,
//                queueLength = doctorQueue.queueLength + 1
//            )
//            queue.add(doctorQueue);
//        } else {
//            // else - queue is not empty
//            // rules:
//            // find the doctor with :
//            // 1. Lowest current total consultation time with other patient
//            // 2. The fastest average consultation time
//
//            val doctorQueue: DoctorQueue? = queue.minWithOrNull(
//                compareByDescending<DoctorQueue> { it.totalEstimatedTimeInMinutes }.thenBy { it.queueLength}
//            )
//        }
    }

    private fun findDoctorQueue(): DoctorQueue {
//        println("==FIND DOCTOR QUEUE==")
        // find the lowest estimated time doctor queues
        // sort the list by the lowest estimated time
        val sortedList : List<DoctorQueue> = doctorQueues.sortedBy { it.totalEstimatedTimeInMinutes }
        if (sortedList.first().queueLength <= 0){
            return sortedList.first()
        }
        val numberOfSameQueueTime: Int = doctorQueues.count { it.totalEstimatedTimeInMinutes == sortedList.first().totalEstimatedTimeInMinutes }
//        println("sorted list: ")
//        for (doctorQueue in sortedList) {
//            print(" ${doctorQueue.doctor.name},")
//        }
//        println()
//        println("first item time: ${sortedList.first().totalEstimatedTimeInMinutes}")
//        println("numberOfSameQueueTime: $numberOfSameQueueTime")
        if (numberOfSameQueueTime == 1){
            return sortedList.first()
        } else {
            // if there is more than one queue with same total of estimated time
            // then find the lowest doctor average consultation time
            val sameTimeList = sortedList.take(numberOfSameQueueTime)

            val doctorQueue = sameTimeList.minWith(
                compareBy<DoctorQueue> { it.consultationTimeInMinutes }
            )
            return doctorQueue
        }
    }

    private fun dequeue() {
//        queue.removeAt(1)
//        val newQueue = arrayListOf<PatientQueueItem>()
//        queue.forEach { item ->
//            val newPosition = item.queuePosition - 1
//            val newEstimatedTime = item.estimatedTimeInMinutes - item.doctor.consultationTimeInMinutes
//            val newItem = item.copy(queuePosition = newPosition, estimatedTimeInMinutes = newEstimatedTime,)
//            newQueue.add(newItem)
//        }
//        queue.clear()
//        queue.addAll(newQueue)
    }

    private fun getDoctorById(id: String): Doctor? {
        return doctorList.find { it.id == id }
    }

    fun printQueueInfo() {
        println()
        println("QUEUE INFO")
        doctorQueues.forEach { queue ->
            println("Doctor: ${queue.doctorId}")
            println("Queue Length: ${queue.queueLength}")
            queue.patientQueueList.forEach { queueItem ->
                println("position: ${queueItem.queuePosition}, patient: ${queueItem.patientId}, estimateTime: ${queueItem.estimatedTimeInMinutes}minutes")
            }
            println("Total Estimated time: ${queue.totalEstimatedTimeInMinutes}")
            println()
        }
    }
}

data class DoctorQueue(
//    var doctor: Doctor,
    val doctorId: String,
    var patientQueueList: ArrayList<PatientQueueItem>,
    var totalEstimatedTimeInMinutes: Int,
    val consultationTimeInMinutes: Int,
    var queueLength: Int,
)

data class PatientQueueItem(
//    var patient: Patient,
//    var doctor: Doctor,
    var patientId: String,
    var doctorId: String,
    var estimatedTimeInMinutes: Int,
    var queuePosition: Int,
)
