using namespace std;
#include <cmath>
#include <algorithm>
#include <cmath>
#include <vector>
#include <map>
#include <string>
#include <iostream>
class KDE {
    double binwidth;
    double minPrice;
    double maxPrice;

    // Function to generate a histogram for prices based on volume
    map<double, double> generateHistogram(const vector<int>& prices, const vector<int>& volumes, int numBins) {
        // Find the minimum and maximum price
        int minPrice = *min_element(prices.begin(), prices.end());
        int maxPrice = *max_element(prices.begin(), prices.end());

        // Calculate bin width
        int binWidth = (maxPrice - minPrice) / numBins;

        // Create a map to store counts for each volume range
        map<double, double> histogram;

        // Iterate through prices and populate the histogram
        for (size_t i = 0; i < volumes.size(); ++i) {
            int bin = (prices[i] - minPrice) / binWidth;
            histogram[bin] += volumes[i];
        }

        return histogram;
    }

    // Function to display the histogram
    void displayHistogram(const map<int, int>& histogram) {
        for (const auto& entry : histogram) {
            cout << "Bin " << entry.first << ": " << string(entry.second, '*') << endl;
        }
    }


    double gaussianKernel(double x, double mean, double bandwidth) {
        double z = (x - mean) / bandwidth;
        return exp(-0.5 * z * z) / (bandwidth * sqrt(2.0 * M_PI));
    }

    double kernelDensityEstimationIndiv(const vector<double>& histogram, double x, double bandwidth) {
        double pdf = 0.0;

        // Iterate through data points and contribute to the density estimate
        for (double dataPoint : histogram) {
            pdf += gaussianKernel(x, dataPoint, bandwidth);
        }

        // Normalize by the number of data points and bandwidth
        pdf /= (histogram.size() * bandwidth);

        return pdf;
    }

    vector<double> kernelDensityEstimation(const vector<double>& histogram, double x, double bandwidth) {
        std::vector<double> result;
        result.reserve(histogram.size());

        // Apply kernel density estimation to each data point
        std::transform(histogram.begin(), histogram.end(), std::back_inserter(result), [this, &histogram, bandwidth](double x) {
            return kernelDensityEstimationIndiv(histogram, x, bandwidth);
        });

        return result;
    }

    vector<double> prominence(){

    }



};