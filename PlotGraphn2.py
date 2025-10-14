import matplotlib.pyplot as plt
import numpy as np
import math

# --- 1. Data and Constants (Extracted from Latest Java Output) ---

# N Values (Input Sizes)
N_values = np.array([80, 100, 200, 500, 800, 1000, 5000, 10000, 15000, 20000, 40000, 80000, 100000, 300000])

# Derived Scaling Constants (C = Exp_Time / Raw_Theoretical_Value at N=300000)
C2 = 2.852077e-10      # for O(n^2)

# New Experimental Times (in milliseconds, derived from the "Exp" column)
exp_n2 = np.array([0.2726, 0.0449, 0.0658, 0.2253, 0.0753, 0.1603, 0.3341, 1.0928, 0.5718, 1.2969, 3.9224, 4.9278, 10.3104, 29.5935])

# --- 2. Theoretical Function Definitions ---

def theoretical_n2(N, C):
    """Calculates scaled O(N^2) time."""
    return C * N**2


# --- 3. Plot Generation ---


# --- Plot: O(n^2) ---
plt.figure(figsize=(10, 6))
plt.style.use('seaborn-v0_8-whitegrid')

plt.plot(N_values, exp_n2, 'o--', color='red', label='Experimental $O(n^2)$ Time')
plt.plot(N_values, theoretical_n2(N_values, C2), '-', color='darkred', linewidth=2, label='Theoretical $O(n^2)$ Curve (Scaled)')

plt.title('Complexity Comparison: Quadratic $O(n^2)$')
plt.xlabel('Input Size (N)')
plt.ylabel('Time (Milliseconds)')
plt.legend()
plt.xscale('log')
plt.ylim(0, max(exp_n2) * 1.1)
plt.grid(True, which="both", ls="--")

plt.tight_layout()
plt.show()