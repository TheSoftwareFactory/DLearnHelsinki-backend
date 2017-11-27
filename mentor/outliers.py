#!/usr/bin/python3
# -*- coding: utf8 -*-

import numpy as np

"""Computes the euclidean distance of vectors u and v.
   1. iterates over the vector features and calculates their squared difference
   2. sums the squared difference vector
   3. returns the square root of the sum"""
def euclidean_distance(u, v): return np.sqrt(np.sum([(au-av)**2 for au,av in zip(u, v)])) 

