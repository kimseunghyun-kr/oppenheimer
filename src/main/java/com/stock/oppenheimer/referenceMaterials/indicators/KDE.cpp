#include <cmath>
#include <vector>
#include <map>
#include <algorithm>

class KDE{

private{
    std::map<double,double> cumulativeVolumeHistogram
}

public{
// Function to generate a histogram for prices based on volume
    void generateHistogram(const std::vector<double>& prices, const std::vector<double>& volumes, int numBins) {
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

    this->cumulativeVolumeHistogram = histogram;
}

    // Function to calculate the Gaussian kernel
    double gaussianKernel(double x, double mean, double bandwidth) {
        double z = (x - mean) / bandwidth;
        return exp(-0.5 * z * z) / (bandwidth * sqrt(2.0 * M_PI));
    }

    // Function to perform Kernel Density Estimation
    double pointKernelDensityEstimation(const std::vector<double>& data, double x, double bandwidth) {
        double pdf = 0.0;

        // Iterate through data points and contribute to the density estimate
        for (double dataPoint : data) {
            pdf += gaussianKernel(x, dataPoint, bandwidth);
        }

        // Normalize by the number of data points and bandwidth
        pdf /= (data.size() * bandwidth);

        return pdf;
    }



    double wholeKernelDensityEstimation(double bandwidth){
        std::vector<double>& volume = std::transform(this.cumulativeVolumeHistogram.begin(),
                                                     this.cumulativeVolumeHistogram.end(),
                                                     std::back_inserter(valueList),
                                                     [](const auto& pair) { return pair.second; });
        // Update cumulativeVolumeHistogram with kernel density estimates
        auto it = this->cumulativeVolumeHistogram.begin();
        for (double value : valueList) {
            it->second = pointKernelDensityEstimation(valueList, value, bandwidth);
            ++it;
        }
    }
}
}
