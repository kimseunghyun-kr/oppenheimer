#include <cmath>
#include <algorithm>
#include <math>
#include <vector>
#include <map>

class KDE {

    // Function to generate a histogram for prices based on volume
    std::map<int, int> generateHistogram(const std::vector<int>& prices, const std::vector<int>& volumes, int numBins) {
        // Find the minimum and maximum volume
        int minVolume = *std::min_element(volumes.begin(), volumes.end());
        int maxVolume = *std::max_element(volumes.begin(), volumes.end());

        // Calculate bin width
        int binWidth = (maxVolume - minVolume) / numBins;

        // Create a map to store counts for each volume range
        std::map<int, int> histogram;

        // Iterate through prices and populate the histogram
        for (size_t i = 0; i < prices.size(); ++i) {
            int bin = (volumes[i] - minVolume) / binWidth;
            histogram[bin] += volumes[i];
        }

        return histogram;
    }

    // Function to display the histogram
    void displayHistogram(const std::map<int, int>& histogram) {
        for (const auto& entry : histogram) {
            std::cout << "Bin " << entry.first << ": " << std::string(entry.second, '*') << std::endl;
        }
    }


    double gaussianKernel(double x, double mean, double bandwidth) {
        double z = (x - mean) / bandwidth;
        return exp(-0.5 * z * z) / (bandwidth * sqrt(2.0 * M_PI));
    }

    double kernelDensityEstimationIndiv(const std::vector<double>& data, double x, double bandwidth) {
        double pdf = 0.0;

        // Iterate through data points and contribute to the density estimate
        for (double dataPoint : data) {
            pdf += gaussianKernel(x, dataPoint, bandwidth);
        }

        // Normalize by the number of data points and bandwidth
        pdf /= (data.size() * bandwidth);

        return pdf;
    }

    double kernelDensityEstimation(const std::vector<double>& data, double x, double bandwidth) {
        std::transform(data.begin(),data.end(),data.begin(), [](int x) kernelDensityEstimationIndiv(data, x , bandwidth));
    }

    vector<double> prominence()



};
