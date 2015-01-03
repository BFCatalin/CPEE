using CPEE.DataReaders;
using CPEE.Models;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Runtime.Serialization.Json;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace CPEE
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        DocumentReader reader;
        public MainWindow()
        {
            InitializeComponent();
            reader = new DocumentReader();
            var data = reader.ReadEmptyDocument();
            this.DataContext = data;
        }

        private void Button_Click_SaveDocument(object sender, RoutedEventArgs e)
        {
            try
            {
                Microsoft.Win32.SaveFileDialog dlg = new Microsoft.Win32.SaveFileDialog();
                dlg.FileName = "Componenta pret energie electrica";
                dlg.DefaultExt = ".cpee";
                dlg.Filter = "Documente CPEE (.cpee)|*.cpee";

                Nullable<bool> result = dlg.ShowDialog();

                if (result == true)
                {
                    string filename = dlg.FileName;
                    DataContractJsonSerializer serializer = new DataContractJsonSerializer(this.DataContext.GetType());
                    using (var fileStream = File.Create(filename))
                    {
                        serializer.WriteObject(fileStream, this.DataContext);
                        ((CPEEDocument)this.DataContext).IsDirty = false;
                        MessageBox.Show("Fisierul a fost salvat cu succes", "Succes", MessageBoxButton.OK, MessageBoxImage.Information);
                    }
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show("Eroare la salvare: " + ex.Message, "Eroare", MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }

        private void Button_Click_OpenDocument(object sender, RoutedEventArgs e)
        {
            try
            {
                Microsoft.Win32.OpenFileDialog dlg = new Microsoft.Win32.OpenFileDialog();
                dlg.DefaultExt = ".cpee";
                dlg.Filter = "Documente CPEE (.cpee)|*.cpee";

                Nullable<bool> result = dlg.ShowDialog();

                if (result == true)
                {
                    string filename = dlg.FileName;
                    var document = reader.ReadDocument(filename);
                    if (document != null)
                    {
                        this.DataContext = document;
                    }
                    else
                    {
                        MessageBox.Show("Documentul nu este un document CPEE!", "Eroare", MessageBoxButton.OK, MessageBoxImage.Error);
                    }
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show("Eroare la deschidere: " + ex.Message, "Eroare", MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }

        private void Button_Click_AddPrice(object sender, RoutedEventArgs e)
        {
            try
            {
                NewPrice priceDlg = new NewPrice();
                if (priceDlg.ShowDialog() == true)
                {
                    CPEEDocument doc = this.DataContext as CPEEDocument;
                    if (doc != null)
                    {
                        int maxIdx = 1;
                        if (doc.Prices.Count > 0)
                        {
                            maxIdx = doc.Prices.Max(price => price.Id);
                        }
                        Price newPrice = priceDlg.DataContext as Price;
                        if (newPrice != null)
                        {
                            newPrice.Id = maxIdx + 1;
                            doc.Prices.Add(newPrice);
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show("Eroare la adaugare pret: " + ex.Message, "Eroare", MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }

        private void Button_Click_RemovePrice(object sender, RoutedEventArgs e)
        {
            try
            {
                Price price = lstPrices.SelectedItem as Price;
                if (price != null)
                {
                    if (price.CountyCount == 0)
                    {
                        ((CPEEDocument)this.DataContext).Prices.Remove(price);
                    }
                    else MessageBox.Show("Pretul nu se poate sterge pentru ca sunt judete cu acest pret.", "Eroare", MessageBoxButton.OK, MessageBoxImage.Error);
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show("Eroare la stergere pret: " + ex.Message, "Eroare", MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }

        private void Button_Click_AddCounty(object sender, RoutedEventArgs e)
        {
            try
            {
                NewCounty countyDlg = new NewCounty();
                NewCountyViewModel newCountyViewModel = new NewCountyViewModel();
                newCountyViewModel.Prices = ((CPEEDocument)this.DataContext).Prices;
                countyDlg.DataContext = newCountyViewModel;
                if (countyDlg.ShowDialog() == true)
                {
                    CPEEDocument doc = this.DataContext as CPEEDocument;
                    if (doc != null)
                    {
                        int maxIdx = 1;
                        if (doc.Counties.Count > 0)
                        {
                            maxIdx = doc.Counties.Max(county => county.Id);
                        }
                        NewCountyViewModel newCounty = countyDlg.DataContext as NewCountyViewModel;
                        if (newCounty != null)
                        {
                            newCounty.County.Id = maxIdx + 1;
                            doc.Counties.Add(newCounty.County);
                            //((CPEEDocument)this.DataContext).Refresh();
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show("Eroare la adaugare judet: " + ex.Message, "Eroare", MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }

        private void Button_Click_RemoveCounty(object sender, RoutedEventArgs e)
        {
            try
            {
                County county = lstCounties.SelectedItem as County;
                if (county != null)
                {
                    ((CPEEDocument)this.DataContext).Counties.Remove(county);
                    //((CPEEDocument)this.DataContext).Refresh();
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show("Eroare la stergere judet: " + ex.Message, "Eroare", MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }

        private void Window_Closing(object sender, System.ComponentModel.CancelEventArgs e)
        {
            if (((CPEEDocument)this.DataContext).IsDirty)
            {
                if (MessageBox.Show("Salvati modificarile inainte de a inchide aplicatia?", "Document modificat", MessageBoxButton.YesNo, MessageBoxImage.Question) == MessageBoxResult.Yes)
                {
                    Button_Click_SaveDocument(sender, new RoutedEventArgs());
                    if (((CPEEDocument)this.DataContext).IsDirty)
                    {
                        e.Cancel = true;
                    }
                }
            }
        }
    }
}
