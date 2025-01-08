//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
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

    val patientList = arrayListOf<Patient>()

    val queueSystem = QueueSystem()
    queueSystem.initQueueSystem(doctorList = doctorList)

    for (i in 1..11){
        val patient = Patient(
            id = "P-$i",
            name = "Patient $i"
        )
//        patientList.add(patient)
        queueSystem.addPatient(patient)
    }

    queueSystem.printQueueInfo()

//    queueSystem.addPatient(
//        patientList.first()
//    )
}
