#ifndef _abstract_search_hpp
#define _abstract_search_hpp

#include <solution.hpp>
#include <evalCC.hpp>

#include <string>
#include <fstream>
#include <iostream>
#include <time.h>

class AbstractSearch {
public:
  AbstractSearch(int evalMax, int fitnessMin, std::string fileName): _evalMax(evalMax)
								   , _fitnessMin(fitnessMin),
								     _fileName(fileName)
  {
    _execNumber = 10;
  }

  ~AbstractSearch()
  {}

  void randomSolution(Solution & solution) const
  {
    srand(time(NULL));
    for(unsigned i = 0; i < solution.size(); i++) {
      int r = rand() % 101;
      solution[i] = r;
    }
  }

  const Solution & bestSolution() const
  {
    return _bestSolution;
  }

  virtual void run() = 0;

  
  const unsigned int n_stack = 5;
  const unsigned int n_destination = 8;
  const unsigned int solution_size = n_destination + n_stack * n_destination * (n_destination - 1);

protected:
  Solution _bestSolution;

  int _evalMax;
  int _fitnessMin;

  int _execNumber;

  std::string _fileName;
};

#endif
