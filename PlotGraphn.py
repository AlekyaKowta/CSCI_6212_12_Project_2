import matplotlib.pyplot as plt
import numpy as np
import math

# --- 1. Data and Constants (Extracted from Latest Java Output) ---

# N Values (Input Sizes)
N_values = np.array([80, 100, 200, 500, 800, 1000, 5000, 10000, 15000, 20000, 40000, 80000, 100000, 300000])

# Derived Scaling Constants (C = Exp_Time / Raw_Theoretical_Value at N=300000)
C1 = 1.087333e-6       # for O(n)

# New Experimental Times (in milliseconds, derived from the "Exp" column)
exp_n = np.array([0.0033, 0.0039, 0.0062, 0.0112, 0.0162, 0.0199, 0.0925, 0.1412, 0.1347, 0.0806, 0.0371, 0.0801, 0.0868, 0.3153])


# --- 2. Theoretical Function Definitions ---
def theoretical_n(N, C):
    """Calculates scaled O(N) time."""
    return C * N

# --- 3. Plot Generation ---

# --- Plot: O(n) ---
plt.figure(figsize=(10, 6))
plt.style.use('seaborn-v0_8-whitegrid')

plt.plot(N_values, exp_n, 'o--', color='red', label='Experimental $O(n)$ Time')
plt.plot(N_values, theoretical_n(N_values, C1), '-', color='darkred', linewidth=2, label='Theoretical $O(n)$ Curve (Scaled)')

plt.title('Complexity Comparison: Linear $O(n)$')
plt.xlabel('Input Size (N)')
plt.ylabel('Time (Milliseconds)')
plt.legend()
plt.xscale('log')
plt.ylim(0, max(exp_n) * 1.1)
plt.grid(True, which="both", ls="--")

plt.tight_layout()
plt.show()