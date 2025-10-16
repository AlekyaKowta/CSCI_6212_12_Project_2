import matplotlib.pyplot as plt
import numpy as np
import math

# --- 1. Data and Constants (Extracted from Latest Java Output) ---

# N Values (Input Sizes) - Directly from Java output header
N_values_On = np.array([5000, 8000, 10000, 20000, 50000, 100000, 200000, 300000, 500000, 750000, 1000000])

# Derived Scaling Constant (from Java header)
C1 = 3.760700e-06

# New Experimental Times
exp_n = np.array([0.0945, 0.1377, 0.0711, 0.1004, 0.1104, 0.1965, 0.2047, 0.3477, 0.6281, 2.6991, 4.4900])

# --- Theoretical Function Definition ---
def theoretical_n(N, C):
    """Calculates scaled O(N) time."""
    return C * N

# --- 3. Plot Generation ---

# --- Plot: O(n) ---
plt.figure(figsize=(10, 6))
plt.style.use('seaborn-v0_8-whitegrid')

plt.plot(N_values_On, exp_n, 'o--', color='blue', label='Experimental $O(n)$ Time')
plt.plot(N_values_On, theoretical_n(N_values_On, C1), '-', color='darkred', linewidth=2, label='Theoretical $O(n)$ Curve (Scaled)')

plt.title('Complexity Comparison: Linear $O(n)$')
plt.xlabel('Input Size (N)')
plt.ylabel('Time (Milliseconds)')
plt.legend()

plt.ylim(0, max(exp_n) * 1.1)
plt.grid(True, which="both", ls="--")

plt.tight_layout()
plt.show()