using Microsoft.Extensions.DependencyInjection;
using Storage;

namespace TestApp
{
    internal static class Program
    {
        private static ServiceProvider? _serviceProvider;
        public static ServiceProvider? ServiceProvider => _serviceProvider;

        [STAThread]
        static void Main()
        {
            ApplicationConfiguration.Initialize();
            var services = new ServiceCollection();
            ConfigureServices(services);
            _serviceProvider = services.BuildServiceProvider();
            Application.Run(_serviceProvider.GetRequiredService<FormMain>());
        }
        private static void ConfigureServices(ServiceCollection services)
        {
            services.AddTransient<IPatientStorage, PatientFileStorage>();
            services.AddTransient<FormMain>();
            services.AddTransient<FormAddPatient>();
        }
    }
}