using CPEE.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;

namespace CPEE
{
    /// <summary>
    /// Interaction logic for NewPrice.xaml
    /// </summary>
    public partial class NewPrice : Window
    {
        public NewPrice()
        {
            InitializeComponent();
            this.DataContext = new Price();
        }

        private void Button_Click_Ok(object sender, RoutedEventArgs e)
        {
            this.DialogResult = true;
        }
    }
}
