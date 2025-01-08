import model.Doctor
import model.Patient

fun main() {
    val doctorA = Doctor(
        id = "D-1",
        name = "Doctor A",
        consultationTimeInMinutes = 3
    )
    val doctorB = Doctor(
        id = "D-2",
        name = "Doctor B",
        consultationTimeInMinutes = 4
    )

    val doctorList = listOf(
        doctorA,
        doctorB
    )

    val queueSystem = QueueSystem()
    queueSystem.initQueueSystem(doctorList = doctorList)

    for (i in 1..11){
        val patient = Patient(
            id = "P-$i",
            name = "Model.Patient $i"
        )
        queueSystem.addPatient(patient)
    }

    queueSystem.printQueueInfo()
}
