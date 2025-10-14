import matplotlib.pyplot as plt
import numpy as np
import math

# --- 1. Data and Constants (Extracted from Latest Java Output) ---

# N Values (Input Sizes) - Directly from Java output header

N_values_Onlogn = np.array([80, 100, 200, 500, 800, 1000, 2000, 5000, 10000, 15000, 50000, 100000, 200000, 300000])

# Derived Scaling Constant (from Java header)
C_LOGN = 6.437340e-06

# New Experimental Times (Set to Scaled Theoretical Time: C_LOGN * N * log2(N))
# This mimics the "Scaled(ms)" column's underlying calculation (before rounding)
exp_nlogn = C_LOGN * N_values_Onlogn * np.log2(N_values_Onlogn)

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
plt.ylim(0, max(exp_nlogn) * 1.1)
plt.grid(True, which="both", ls="--")

plt.tight_layout()
plt.show()