using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Storage
{
    public class Room
    {
        public int Id { get; set; }
        public int roomNumber { get; set; }
        public List<Patient> Patients { get; set; } = new List<Patient>();

        public Room (int roomNumber)
        {
            this.roomNumber = roomNumber;
        }
    }
}
