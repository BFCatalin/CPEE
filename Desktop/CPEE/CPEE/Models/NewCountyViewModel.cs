using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using CPEE.Extensions;

namespace CPEE.Models
{
    public class NewCountyViewModel : INotifyPropertyChanged
    {
        public event PropertyChangedEventHandler PropertyChanged;
        private County _county;
        private ObservableCollection<Price> _prices;

        public NewCountyViewModel()
        {
            _county = new County();
        }

        public ObservableCollection<Price> Prices
        {
            get { return _prices; }
            set { PropertyChanged.ChangeAndNotify(ref _prices, value, () => Prices); }
        }

        public County County
        {
            get { return _county; }
            set { PropertyChanged.ChangeAndNotify(ref _county, value, () => County); }
        }
    }
}
