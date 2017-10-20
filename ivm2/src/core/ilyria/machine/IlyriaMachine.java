/**
 * بسم الله الرحمن الرحيم
 * رخصة إليريا التعليمية العامة
 *
 * يمكنك استخدامها كيفما شئت
 * لكن لأغراض تعليمية فقط
 * مع مراعات مشاركتك لما أضفته مع الآخرين بنفس الرخصة الحالية
 *
 * كتبها إلياس سعداوي
 * في 23/8/1438
 * الموافق لـ 19/2/2017
 *
 * في أمان الله
 *
 */
package core.ilyria.machine;

/**
 *
 * @author ilyria
 */
public interface IlyriaMachine
{
    //---------------------------declarations-----------------------------------
    int BYTE   = 1;
    int SHORT  = 2;
    int CHAR   = 3;
    int INT    = 4;
    int LONG   = 5;
    int FLOAT  = 6;
    int DOUBLE = 7;
    
    // not for declaration but for String manipulations
    int CHARS  = 8;
    
    /**
     * type of calculation determine the number of bytes treated as Word.
     */
    int TYPE = 9;
    
    //--------------------------Input/Output operations-------------------------
    
    /**
     * Input operations: Read a value(s) from the keyboard into a specific location in memory.
     */
    int READ = 10; 
    
    /**
     * output operations: Write a value(s) from a specific location in memory to the screen.
     */
    int WRITE = 11;
    
    /**
     * write new line.
     */
    int WRITENL = 12;
    
    /**
     * the precision of float and double when printed with write operation.
     */
    int PRECISION = 13;
    
    //---------------------------------Load/store operations--------------------
    
    /**
     * Load operation: Load a value from a specific location in memory into the accumulator.
     */
    int LOAD = 20;
    /**
     * Store operation: Store a value from the accumulator into a specific location in memory.
     */
    int STORE = 21;
    
    //-------------------------Arithmetic operations----------------------------
    
    /**
     * Add a word from a specific location in memory to the word in the
     * accumulator (leave the result in the accumulator).
     */
    int ADD = 30;
    
    /**
     * Subtract a value from a specific location in memory from the value in
     * the accumulator (leave the result in the accumulator).
     */
    int SUB = 31;
    
    /**
     * Divide a value from a specific location in memory into the value in
     * the accumulator (leave result in the accumulator).
     */
    int DIV = 32;
    
    /**
     * Multiply a value from a specific location in memory by the value in
     * the accumulator (leave the result in the accumulator).
     */
    int MUL = 33;
    
    /**
     * Calculate the remainder of dividing of accumulator by the value of
     * specific location in memory.
     */
    int MOD = 34;
    
    /**
     * increment the loop counter register.
     */
    int INC = 35;
    
    /**
     * decrement the loop counter register.
     */
    int DEC = 36;
    
    //----------------------Transfer-of-control operations----------------------
    
    /**
     * Branch to a specific location in memory.
     */
    int BRANCH = 40;
    
    /**
     * Branch to a specific location in memory if the accumulator is negative.
     */
    int BRANCHNEG = 41;
    
    /**
     * Branch to a specific location in memory if the accumulator is zero.
     */
    int BRANCHZERO = 42;
    
    /**
     * Halt: The program has completed its task.
     */
    int HALT = 43;
    
    /**
     * exponent
     */
    int EXP = 50;
    
    /**
     * calculate the square root of value in specific location on memory then
     * asigned it to the accumulator.
     */
    int SQRT = 51;
    
    /**
     * make a specific location as label for branching.
     */
    int LABEL = 70;
    
    /**
     * not implemented yet!.
     */
    int PROCEDURE = 71;
    
    /**
     * not implemented yet!.
     */
    int RETURN = 72;
    
    /**
     * not implemented yet!.
     */
    int CALL = 73;
    
    //-----------------------------memory managment-----------------------------
    
    /**
     * not implemented yet!.
     */
    int DELETE = 80;
    
    //---------------------------default methods--------------------------------
    /**
     * 
     * @return the fetched instruction 
     */
    default int fetch() 
    { 
        throw new RuntimeException( "not implemented yet!" ); 
    } // end default method fetch
    
    /**
     * 
     * @return array of two integers operationCode and operand
     */
    default int[] decode() 
    { 
        throw new RuntimeException( "not implemented yet!" );
    } // end default method decode
    
    /**
     * 
     * @return the successfull or failure of instruction execution
     */
    default int execute() 
    {
        throw new RuntimeException( "not implemented yet!" );
    } // end default method execute
    
    /**
     * reset all registers and RAM contents to 0
     */
    default void reset() 
    {
        throw new RuntimeException( "not implemented yet!" );
    } // end default method reset
} // end interface IlyriaMachine
