#include "SimLib.h"

/**
 * Limits a value to the Victor output range of -1.0 to 1.0.
 * 
 * @param f The value to limit
 */
float SimLib::LimitOutput(float f)
{
	if (f > 1.0)
	{
		return 1.0;
	}
	else if (f < -1.0)
	{
		return -1.0;
	}
	return f;
}

/**
 * Squares a value while preserving sign.
 * 
 * @param f The value to square
 */
float SimLib::SignSquare(float f)
{	
	if (f < 0)
	{	
		return -1.0 * f * f;
	}
	else
	{
		return f * f;
	}
}
