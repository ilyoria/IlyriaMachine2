
import core.ilyria.machine.unit.CPU;
import core.ilyria.machine.unit.ExecutionUnit;
import java.nio.ByteBuffer;


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

/**
 *
 * @author ilyria
 */
public class Boot
{
    /** the Central Processing Unit */
    private static CPU cpu;
    
    // begin execution of IlyriaMachine
    public static void main( String[] args )
    {
        rom();
    } // end main
    
    
    private static void rom()
    {   
        // init the Central Processing Unit (CPU) and bind it with RAM
        cpu = new CPU();
        
        ExecutionUnit execution = cpu.getExecutionUnit();
        System.out.printf( "INSTRUCTION_LENGTH : %d%n", execution.INSTRUCTION_LENGTH() );
        System.out.printf( "END_INSTRUCTION    : %d%n", execution.END_INSTRUCTION() );
        System.out.printf( "END_DATA           : %d%n", execution.END_DATA() );
        System.out.printf( "END_MEMORY         : %d%n", execution.END_MEMORY() );
        
        int index = 0;
        
        final ByteBuffer ram = cpu.getRam();
        
        ram.putInt( 1_000_000 );
        
    } // end method rom
} // end class Boot
