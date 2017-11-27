#!/usr/bin/python3
# -*- coding: utf8 -*-

from math import sqrt

# Computes the euclidean distance of vectors u and v 
def euclidean_distance(u, v): return sqrt(sum([(au-av)**2 for au,av in zip(u, v)])) 

def k_distance(p):