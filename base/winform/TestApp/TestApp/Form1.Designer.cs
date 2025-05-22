namespace TestApp
{
    partial class FormMain
    {
        /// <summary>
        ///  Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        ///  Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        ///  Required method for Designer support - do not modify
        ///  the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            addPatient = new Button();
            listBox = new ListBox();
            textBoxAge = new TextBox();
            label1 = new Label();
            buttonAge = new Button();
            SuspendLayout();
            // 
            // addPatient
            // 
            addPatient.Location = new Point(639, 12);
            addPatient.Name = "addPatient";
            addPatient.Size = new Size(149, 23);
            addPatient.TabIndex = 0;
            addPatient.Text = "Добавить пациента";
            addPatient.UseVisualStyleBackColor = true;
            addPatient.Click += addPatient_Click;
            // 
            // listBox
            // 
            listBox.FormattingEnabled = true;
            listBox.ItemHeight = 15;
            listBox.Location = new Point(12, 12);
            listBox.Name = "listBox";
            listBox.Size = new Size(621, 424);
            listBox.TabIndex = 1;
            // 
            // textBoxAge
            // 
            textBoxAge.Location = new Point(644, 70);
            textBoxAge.Name = "textBoxAge";
            textBoxAge.Size = new Size(144, 23);
            textBoxAge.TabIndex = 2;
            // 
            // label1
            // 
            label1.AutoSize = true;
            label1.Location = new Point(644, 52);
            label1.Name = "label1";
            label1.Size = new Size(95, 15);
            label1.TabIndex = 3;
            label1.Text = "Введите возраст";
            // 
            // buttonAge
            // 
            buttonAge.Location = new Point(644, 99);
            buttonAge.Name = "buttonAge";
            buttonAge.Size = new Size(144, 23);
            buttonAge.TabIndex = 4;
            buttonAge.Text = "Вывести";
            buttonAge.UseVisualStyleBackColor = true;
            buttonAge.Click += buttonAge_Click;
            // 
            // FormMain
            // 
            AutoScaleDimensions = new SizeF(7F, 15F);
            AutoScaleMode = AutoScaleMode.Font;
            ClientSize = new Size(800, 450);
            Controls.Add(buttonAge);
            Controls.Add(label1);
            Controls.Add(textBoxAge);
            Controls.Add(listBox);
            Controls.Add(addPatient);
            Name = "FormMain";
            Text = "Form1";
            ResumeLayout(false);
            PerformLayout();
        }

        #endregion

        private Button addPatient;
        private ListBox listBox;
        private TextBox textBoxAge;
        private Label label1;
        private Button buttonAge;
    }
}
