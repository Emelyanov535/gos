using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.Json;
using System.Threading.Tasks;

namespace Storage
{
    public class PatientFileStorage : IPatientStorage
    {
        private readonly string filePath = "patients.json";

        public List<Patient> getPatientsOverAge(int age)
        {
            List<Patient> patients = LoadData();

            var olderPatients = from p in patients
                                where p.age > age
                                select p;

            return olderPatients.ToList();
        }

        public List<Patient> LoadData()
        {
            if (!File.Exists(filePath))
                return new List<Patient>();

            string json = File.ReadAllText(filePath);
            return JsonSerializer.Deserialize<List<Patient>>(json);
        }

        public void save(Patient patient)
        {
            List<Patient> patients = LoadData();
            patients.Add(patient);

            string json = JsonSerializer.Serialize(patients);
            File.WriteAllText(filePath, json);
        }
    }
}
