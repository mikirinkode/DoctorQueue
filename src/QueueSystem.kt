import model.Doctor
import model.DoctorQueue
import model.Patient
import model.QueueItem

class QueueSystem {
    private var doctorQueues: ArrayList<DoctorQueue> = arrayListOf()
    private var doctorList: ArrayList<Doctor> = arrayListOf()

    fun initQueueSystem(doctorList: List<Doctor>) {
        this.doctorList.addAll(doctorList)
        doctorList.forEach { doctor ->
            doctorQueues.add(
                DoctorQueue(
                    doctorId = doctor.id,
                    patientQueue = arrayListOf(),
                    totalEstimatedTimeInMinutes = 0,
                    queueLength = 0,
                    consultationTimeInMinutes = doctor.consultationTimeInMinutes
                )
            )
        }
    }

    fun addPatient(patient: Patient) {
        enqueue(patient)
    }

    // function to add patient to a queue
    private fun enqueue(patient: Patient) {

        val selectedDoctorQueue = findDoctorQueue()

        val selectedDoctor: Doctor? = getDoctorById(selectedDoctorQueue.doctorId)

        if (selectedDoctor == null){
            println("NO DOCTOR FOUND")
            return
        }
        val position = selectedDoctorQueue.queueLength + 1
        val estimatedTime = if (position == 1)  0 else selectedDoctor.consultationTimeInMinutes * (position - 1)

        val queueItem = QueueItem(
            queuePosition = position,
            patientId = patient.id,
            doctorId = selectedDoctor.id,
            estimatedTimeInMinutes = estimatedTime,
        )

        selectedDoctorQueue.patientQueue.add(queueItem)

        val newDoctorQueue = selectedDoctorQueue.copy(
            patientQueue = selectedDoctorQueue.patientQueue,
            totalEstimatedTimeInMinutes = estimatedTime,
            queueLength = selectedDoctorQueue.queueLength + 1
        )

        val doctorQueueIndex = doctorQueues.indexOf(selectedDoctorQueue)
        doctorQueues[doctorQueueIndex] = newDoctorQueue
//        doctorQueues.remove(selectedDoctorQueue)
//        doctorQueues.add(newDoctorQueue)

        println("Model.Patient ${patient.name} has been added to ${selectedDoctor.name}'s queue with position #${position} & estimated time: $estimatedTime minutes.")
    }

    private fun findDoctorQueue(): DoctorQueue {
        // sort list by the lowest total estimatedTime and the lowest queue length
//        val sortedList : List<DoctorQueue> = doctorQueues.sortedBy { it.totalEstimatedTimeInMinutes }
        val sortedList : List<DoctorQueue> = doctorQueues.sortedWith(
            compareBy<DoctorQueue> {
                it.totalEstimatedTimeInMinutes
            }.thenBy {
                it.queueLength
            }
        )
        if (sortedList.first().queueLength <= 0){
            return sortedList.first()
        }

        val numberOfSameQueueTime: Int = doctorQueues.count { it.totalEstimatedTimeInMinutes == sortedList.first().totalEstimatedTimeInMinutes }
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
            val doctor = getDoctorById(queue.doctorId)

            println("Doctor: ${doctor?.name}")
            println("Queue Length: ${queue.queueLength}")
            println("Average Consultation time: ${doctor?.consultationTimeInMinutes} minutes")
            queue.patientQueue.forEach { queueItem ->
                println("position: ${queueItem.queuePosition}, patient: ${queueItem.patientId}, estimateTime: ${queueItem.estimatedTimeInMinutes} minutes")
            }
            println()
        }
    }
}
