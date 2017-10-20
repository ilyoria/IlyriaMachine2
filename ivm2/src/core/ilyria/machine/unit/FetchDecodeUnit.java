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
package core.ilyria.machine.unit;

import java.nio.ByteBuffer;

/**
 *
 * @author ilyria
 */
public class FetchDecodeUnit
{
    protected ExecutionUnit execution;
    protected ByteBuffer ram;

    public FetchDecodeUnit( ExecutionUnit execution, ByteBuffer ram )
    {
        this.execution = execution;
        this.ram = ram;
    } // end two-argument FetchDecodeUnit constructor
    
    public int fetch()
    {
        // get the instruction
        int instruction = ram.getInt( execution.getProgramCounter() );
        
        // set instruction register
        execution.setInstruction(instruction);
        
        // increment programCounter
        execution.incrementProgramCounter();
        
        return instruction;
    } // end method fetch
    
    public int[] decode()
    {
        // get instruction as 9 digit String
        String instruction = String.format( 
           "%09d", execution.getInstruction() );
        
        // get operationCode
        // get the two left digit
        String operationCodeStr = instruction.substring( 0, 2 );
        // convert it to int and set operationCode register
        int operationCode = Integer.parseInt( operationCodeStr ) ;
        execution.setOperationCode( operationCode );
        
        // get operand
        // get the remainder String
        String operandStr = instruction.substring( 2, instruction.length() );
        // convert it to int and set operand register
        int operand = Integer.parseInt( operandStr );
        execution.setOperand( operand );
        
        return new int[]{ operationCode, operand };
    } // end method decode
} // end class FetchDecodeUnit
