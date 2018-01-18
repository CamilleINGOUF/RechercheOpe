#ifndef _random_walk_hpp
#define _random_walk_hpp

#include <AbstractSearch.hpp>
#include <evalCC.hpp>

#include <fstream>
#include <iostream>

class RandomWalk : public AbstractSearch
{
public:
  RandomWalk(int execNumber, int evalMax, int fitnessMin) : AbstractSearch(execNumber, evalMax, fitnessMin, "rw.csv")
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
	    srand(time(NULL));
	    int randomIndex = rand() % (s.size() + 1);
	    s.neighbour(randomIndex);
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
