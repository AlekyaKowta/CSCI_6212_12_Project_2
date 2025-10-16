import matplotlib.pyplot as plt
import numpy as np
import math

# --- 1. Data and Constants (Extracted from Latest Java Output) ---

# N Values (Input Sizes) - Directly from Java output header

N_values_Onlogn = np.array([5000, 10000, 15000, 30000, 50000, 100000, 200000, 300000, 400000, 500000, 600000])

# Derived Scaling Constant (from Java header)
C_LOGN = 6.5705e-6

# Experimental Times (Exp(ms))
exp_nlogn = np.array([1.1566, 1.4714, 1.9462, 4.1362, 5.1129, 10.9229, 24.3601, 34.1417, 46.2333, 60.9619, 75.6707])

# --- Theoretical Function Definition ---
def theoretical_nlogn(N, C):
    """Calculates scaled O(N log N) time."""
    # Use np.log2 for log base 2
    return C * N * np.log2(N)


# --- 3. Plot Generation ---

# --- Plot: O(n log n) ---
plt.figure(figsize=(10, 6))
plt.style.use('seaborn-v0_8-whitegrid')

plt.plot(N_values_Onlogn, exp_nlogn, 'o--', color='red', label='Experimental $O(nlogn)$ Time')
plt.plot(N_values_Onlogn, theoretical_nlogn(N_values_Onlogn, C_LOGN), '-', color='darkred', linewidth=2, label='Theoretical $O(nlogn)$ Curve (Scaled)')

plt.title('Complexity Comparison: Logarithmic $O(nlogn)$')
plt.xlabel('Input Size (N)')
plt.ylabel('Time (Milliseconds)')
plt.legend()
plt.xscale('log')
plt.yscale('log')
plt.ylim(0, max(exp_nlogn) * 1.1)
plt.grid(True, which="both", ls="--")

plt.tight_layout()
plt.show()