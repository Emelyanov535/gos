using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Storage
{
    public interface IPatientStorage
    {
        void save(Patient patient);
        List<Patient> LoadData();
        List<Patient> getPatientsOverAge(int age);
    }
}
