using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using CPEE.Extensions;
using System.Runtime.Serialization;

namespace CPEE.Models
{
    public class CPEEDocument : INotifyPropertyChanged
    {
        public event PropertyChangedEventHandler PropertyChanged;

        private GlobalPrices _global;
        private ObservableCollectionEx<Price> _prices;
        private ObservableCollectionEx<County> _counties;
        private bool isDirty = false;

        public CPEEDocument() 
        {
            isDirty = false;
            _global = new GlobalPrices();
            _prices = new ObservableCollectionEx<Price>();
            _counties = new ObservableCollectionEx<County>();
        }

        public GlobalPrices Global
        {
            get { return _global; }
            set { PropertyChanged.ChangeAndNotify(ref _global, value, () => Global); isDirty = true; Global.PropertyChanged += Global_PropertyChanged; }
        }

        public ObservableCollectionEx<Price> Prices
        {
            get { return _prices; }
            set { PropertyChanged.ChangeAndNotify(ref _prices, value, () => Prices); isDirty = true; }
        }

        public ObservableCollectionEx<County> Counties
        {
            get { return _counties; }
            set { PropertyChanged.ChangeAndNotify(ref _counties, value, () => Counties); isDirty = true; }
        }

        public void Refresh()
        {
            Counties.CollectionChanged -= Counties_CollectionChanged;
            Counties.ItemPropertyChanged -= ItemPropertyChanged;
            Prices.ItemPropertyChanged -= ItemPropertyChanged;
            Prices.CollectionChanged -= Prices_CollectionChanged;
            if (Counties.Count > 0)
            {
                Counties.ForEach(county =>
                {
                    county.Price = Prices.FirstOrDefault(price => price.Id == county.PriceId);
                });
                RecountPriceCounties();
            }
            Counties.CollectionChanged += Counties_CollectionChanged;
            Counties.ItemPropertyChanged += ItemPropertyChanged;
            Prices.CollectionChanged += Prices_CollectionChanged;
            Prices.ItemPropertyChanged += ItemPropertyChanged;
        }

        void ItemPropertyChanged(object sender, PropertyChangedEventArgs e)
        {
            isDirty = true;
        }

        void Prices_CollectionChanged(object sender, System.Collections.Specialized.NotifyCollectionChangedEventArgs e)
        {
            isDirty = true;
        }

        void Counties_CollectionChanged(object sender, System.Collections.Specialized.NotifyCollectionChangedEventArgs e)
        {
            RecountPriceCounties();
            isDirty = true;
        }

        void Global_PropertyChanged(object sender, PropertyChangedEventArgs e)
        {
            isDirty = true;
        }

        private void RecountPriceCounties()
        {
            if (Counties.Count > 0)
            {
                Prices.ForEach(price =>
                {
                    price.CountyCount = Counties.Count(county => county.PriceId == price.Id);
                });
            }
        }

        [IgnoreDataMember]
        public bool IsDirty
        {
            get { return isDirty; }
            set { isDirty = value; }
        }
    }
}
