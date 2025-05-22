using Storage;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace TestApp
{
    public partial class FormAddPatient : Form
    {
        private readonly IPatientStorage patientStorage;
        public FormAddPatient(IPatientStorage patientStorage)
        {
            InitializeComponent();
            this.patientStorage = patientStorage;
        }

        private void buttonSave_Click(object sender, EventArgs e)
        {
            Patient patient = new Patient(
                textBoxName.Text,
                textBoxSurname.Text,
                Convert.ToInt32(textBoxAge.Text),
                textBoxDiagnos.Text
                );

            patientStorage.save(patient);

            DialogResult = DialogResult.OK;
            Close();
        }

        private void buttonCancel_Click(object sender, EventArgs e)
        {
            DialogResult = DialogResult.Cancel;
            Close();
        }
    }
}
