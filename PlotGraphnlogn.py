import matplotlib.pyplot as plt
import numpy as np
import math

# --- 1. Data and Constants (Extracted from Latest Java Output) ---

# N Values (Input Sizes)
N_values = np.array([80, 100, 200, 500, 800, 1000, 5000, 10000, 15000, 20000, 40000, 80000, 100000, 300000])

# Derived Scaling Constants (C = Exp_Time / Raw_Theoretical_Value at N=300000)
C_LOGN = 6.330896e-6   # for O(n log n)

# New Experimental Times (in milliseconds, derived from the "Exp" column)
exp_nlogn = np.array([0.0614, 0.0348, 0.0636, 0.1008, 0.1883, 0.2236, 0.6675, 1.5017, 1.9921, 2.3901, 3.6953, 7.8384, 9.7343, 34.2449])

# --- 2. Theoretical Function Definitions ---

def theoretical_nlogn(N, C):
    """Calculates scaled O(N log N) time."""
    # Use np.log2 for log base 2
    # Ensure no log(0) for N=1 if it were present, but N >= 80 here.
    return C * N * np.log2(N)


# --- 3. Plot Generation ---

# --- Plot: O(n log n) ---
plt.figure(figsize=(10, 6))
plt.style.use('seaborn-v0_8-whitegrid')

plt.plot(N_values, exp_nlogn, 'o--', color='red', label='Experimental $O(nlogn)$ Time')
plt.plot(N_values, theoretical_nlogn(N_values, C_LOGN), '-', color='darkred', linewidth=2, label='Theoretical $O(nlogn)$ Curve (Scaled)')

plt.title('Complexity Comparison: Logarithmic $O(nlogn)$')
plt.xlabel('Input Size (N)')
plt.ylabel('Time (Milliseconds)')
plt.legend()
plt.xscale('log')
plt.ylim(0, max(exp_nlogn) * 1.1)
plt.grid(True, which="both", ls="--")

plt.tight_layout()
plt.show()