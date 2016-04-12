using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace S4ClientApi.Service
{
    public class ClassificationCategory
    {
        private String Label;
        private double Score;

        public ClassificationCategory()
        {

        }

        public ClassificationCategory(String category, double confidence)
        {
            this.Label = category;
            this.Score = confidence;
        }

        /// <summary>
        /// The type label of the specified category
        /// </summary>
        public String label
        {
            get { return Label; }
            set { Label = value; }
        }

        /// <summary>
        /// The confidence score of the specified category
        /// </summary>
        public double score
        {
            get { return Score; }
            set { Score = value; }
        }
    }
}
