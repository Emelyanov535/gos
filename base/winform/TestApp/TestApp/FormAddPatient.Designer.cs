namespace TestApp
{
    partial class FormAddPatient
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
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
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            textBoxName = new TextBox();
            textBoxSurname = new TextBox();
            textBoxAge = new TextBox();
            textBoxDiagnos = new TextBox();
            label1 = new Label();
            label2 = new Label();
            label3 = new Label();
            label4 = new Label();
            buttonSave = new Button();
            buttonCancel = new Button();
            SuspendLayout();
            // 
            // textBoxName
            // 
            textBoxName.Location = new Point(12, 12);
            textBoxName.Name = "textBoxName";
            textBoxName.Size = new Size(163, 23);
            textBoxName.TabIndex = 0;
            // 
            // textBoxSurname
            // 
            textBoxSurname.Location = new Point(12, 46);
            textBoxSurname.Name = "textBoxSurname";
            textBoxSurname.Size = new Size(163, 23);
            textBoxSurname.TabIndex = 1;
            // 
            // textBoxAge
            // 
            textBoxAge.Location = new Point(12, 75);
            textBoxAge.Name = "textBoxAge";
            textBoxAge.Size = new Size(163, 23);
            textBoxAge.TabIndex = 2;
            // 
            // textBoxDiagnos
            // 
            textBoxDiagnos.Location = new Point(12, 104);
            textBoxDiagnos.Name = "textBoxDiagnos";
            textBoxDiagnos.Size = new Size(163, 23);
            textBoxDiagnos.TabIndex = 3;
            // 
            // label1
            // 
            label1.AutoSize = true;
            label1.Location = new Point(181, 20);
            label1.Name = "label1";
            label1.Size = new Size(31, 15);
            label1.TabIndex = 4;
            label1.Text = "Имя";
            // 
            // label2
            // 
            label2.AutoSize = true;
            label2.Location = new Point(179, 54);
            label2.Name = "label2";
            label2.Size = new Size(58, 15);
            label2.TabIndex = 5;
            label2.Text = "Фамилия";
            // 
            // label3
            // 
            label3.AutoSize = true;
            label3.Location = new Point(179, 83);
            label3.Name = "label3";
            label3.Size = new Size(50, 15);
            label3.TabIndex = 6;
            label3.Text = "Возраст";
            // 
            // label4
            // 
            label4.AutoSize = true;
            label4.Location = new Point(179, 112);
            label4.Name = "label4";
            label4.Size = new Size(52, 15);
            label4.TabIndex = 7;
            label4.Text = "Диагноз";
            // 
            // buttonSave
            // 
            buttonSave.Location = new Point(12, 133);
            buttonSave.Name = "buttonSave";
            buttonSave.Size = new Size(106, 23);
            buttonSave.TabIndex = 8;
            buttonSave.Text = "Сохранить";
            buttonSave.UseVisualStyleBackColor = true;
            buttonSave.Click += buttonSave_Click;
            // 
            // buttonCancel
            // 
            buttonCancel.Location = new Point(124, 133);
            buttonCancel.Name = "buttonCancel";
            buttonCancel.Size = new Size(113, 23);
            buttonCancel.TabIndex = 9;
            buttonCancel.Text = "Отмена";
            buttonCancel.UseVisualStyleBackColor = true;
            buttonCancel.Click += buttonCancel_Click;
            // 
            // FormAddPatient
            // 
            AutoScaleDimensions = new SizeF(7F, 15F);
            AutoScaleMode = AutoScaleMode.Font;
            ClientSize = new Size(244, 166);
            Controls.Add(buttonCancel);
            Controls.Add(buttonSave);
            Controls.Add(label4);
            Controls.Add(label3);
            Controls.Add(label2);
            Controls.Add(label1);
            Controls.Add(textBoxDiagnos);
            Controls.Add(textBoxAge);
            Controls.Add(textBoxSurname);
            Controls.Add(textBoxName);
            Name = "FormAddPatient";
            Text = "AddPatient";
            ResumeLayout(false);
            PerformLayout();
        }

        #endregion

        private TextBox textBoxName;
        private TextBox textBoxSurname;
        private TextBox textBoxAge;
        private TextBox textBoxDiagnos;
        private Label label1;
        private Label label2;
        private Label label3;
        private Label label4;
        private Button buttonSave;
        private Button buttonCancel;
    }
}