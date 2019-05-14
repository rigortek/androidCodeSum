cmake_minimum_required(VERSION 3.13)
project(BoostProj)

set(CMAKE_CXX_STANDARD 11)
SET(CMAKE_CXX_FLAGS -pthread)

add_executable(BoostProj main.cpp)