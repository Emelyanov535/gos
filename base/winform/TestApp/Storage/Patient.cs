using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Storage
{
    public class Patient
    {
        public int Id { get; set; }
        public String name { get; set; }
        public String surname { get; set; }
        public int age { get; set; }
        public String diagnos { get; set; }

        public int RoomId { get; set; }
        public Room Room { get; set; }

        public Patient(string name, string surname, int age, string diagnos)
        {
            this.name = name;
            this.surname = surname;
            this.age = age;
            this.diagnos = diagnos;
        }
    }
}
