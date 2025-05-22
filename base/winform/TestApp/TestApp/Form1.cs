using Storage;

namespace TestApp
{
    public partial class FormMain : Form
    {

        private readonly IPatientStorage patientStorage;
        public FormMain(IPatientStorage patientStorage)
        {
            InitializeComponent();
            this.patientStorage = patientStorage;
            LoadData();
        }

        private void addPatient_Click(object sender, EventArgs e)
        {
            var service = Program.ServiceProvider?.GetService(typeof(FormAddPatient));
            if (service is FormAddPatient form)
            {
                if (form.ShowDialog() == DialogResult.OK)
                {
                    LoadData();
                }
            }
        }

        private void LoadData()
        {
            listBox.Items.Clear();

            var patients = patientStorage.LoadData();
            foreach (var p in patients)
            {
                listBox.Items.Add($"{p.name} {p.surname}, {p.age} лет Ч {p.diagnos}");
            }
        }

        private void buttonAge_Click(object sender, EventArgs e)
        {
            listBox.Items.Clear();
            int age = Convert.ToInt32(textBoxAge.Text);

            var patients = patientStorage.getPatientsOverAge(age);

            foreach (var p in patients)
            {
                listBox.Items.Add($"{p.name} {p.surname}, {p.age} лет Ч {p.diagnos}");
            }
        }
    }
}
