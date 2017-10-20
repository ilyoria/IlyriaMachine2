
import java.util.Stack;


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
public class Main
{

    /**
     * @param args the command line arguments
     */
    public static void main( String[] args )
    {
        Stack< String > stack = new Stack<>();
        
        stack.push( "ilyes" );
        stack.push( "Houria" );
        stack.push( "Radjaa" );
        
        while ( !stack.isEmpty() )
        {
            System.out.println( stack.pop() );
        } // end while
    } // end main
    
    
    
} // end class Main

class ProcedureFrame
{
    
} // end class ProcedureFrame
