import matplotlib.pyplot as plt
import numpy as np
import math

# --- 1. Data and Constants (Extracted from Latest Java Output) ---

# N Values (Input Sizes) - Directly from Java output header
N_values_On2 = np.array([20, 40, 50, 80, 100, 200, 500, 1000, 2000, 5000, 8000, 10000, 15000, 20000])

# Derived Scaling Constant (from Java header)
C2 = 2.303250e-09

# New Experimental Times
exp_n2 = np.array([0.0620, 0.0147, 0.0234, 0.0361, 0.0783, 0.0958, 0.2739, 0.1646, 0.4287, 0.3590, 0.4943, 0.4733, 0.8944, 2.4858])

# --- Theoretical Function Definition ---
def theoretical_n2(N, C):
    """Calculates scaled O(N^2) time."""
    return C * N**2


# --- 3. Plot Generation ---


# --- Plot: O(n^2) ---
plt.figure(figsize=(10, 6))
plt.style.use('seaborn-v0_8-whitegrid')

plt.plot(N_values_On2, exp_n2, 'o--', color='red', label='Experimental $O(n^2)$ Time')
plt.plot(N_values_On2, theoretical_n2(N_values_On2, C2), '-', color='darkred', linewidth=2, label='Theoretical $O(n^2)$ Curve (Scaled)')

plt.title('Complexity Comparison: Quadratic $O(n^2)$')
plt.xlabel('Input Size (N)')
plt.ylabel('Time (Milliseconds)')
plt.legend()
# plt.xscale('log')
# plt.yscale('log')
plt.ylim(0, max(exp_n2) * 1.1)
plt.grid(True, which="both", ls="--")

plt.tight_layout()
plt.show()