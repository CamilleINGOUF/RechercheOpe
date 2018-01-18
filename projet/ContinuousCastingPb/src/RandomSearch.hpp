#ifndef _random_search_hpp
#define _random_search_hpp

#include <AbstractSearch.hpp>
#include <evalCC.hpp>

#include <fstream>
#include <iostream>

class RandomSearch : public AbstractSearch
{
public:
  RandomSearch(int execNumber, int evalMax, int fitnessMin) : AbstractSearch(execNumber, evalMax, fitnessMin, "rs.csv")
  {}

  void run()
  {
    EvalCC eval;
    Solution s;
    s.resize(solution_size, 1);
    
    std::ofstream outputfile;
    outputfile.open(_fileName);
    outputfile << "fitness fitnessMin nbeval nbEvalMax\n";

    for(int i = 0; i < _execNumber; i++)
      {
	randomSolution(s);

	bool isOver = false;
	int bestFitness = s.fitness();
	int currentFitness = bestFitness;

	int evalNumber = 0;
    
	while(!isOver)
	  {
	    randomSolution(s);
	    eval(s);
	    evalNumber++;

	    currentFitness = s.fitness();

	     if(currentFitness <= bestFitness)
	      {
		bestFitness = currentFitness;
		_bestSolution = s;
	      }

	    if(evalNumber >= _evalMax or bestFitness <= _fitnessMin)
	      isOver = true;
	  }

	std::cout << i << std::endl;

	outputfile << bestFitness << " " <<
	  _fitnessMin << " " <<
	  evalNumber << " " <<
	  _evalMax << " " <<
	  std::endl;
      }
  }
};

#endif
