package nikolay.egorov.cli.analysis.syntax.visitor

import nikolay.egorov.cli.analysis.syntax.representation.AssignStatement
import nikolay.egorov.cli.analysis.syntax.representation.CommandStatement
import nikolay.egorov.cli.commands.AbstractCommand
import nikolay.egorov.cli.commands.CommandCreator
import nikolay.egorov.cli.commands.ExecutableInterface

/**
 * Builds instances of ExecutableInterface meaning commands with needed args
 *
 * @see ExecutableInterface
 * @see CommandCreator
 */
class PostVisitor : Visitor() {

    val executableTasks: ArrayList<AbstractCommand> = ArrayList()

    /**
     * Invokes CommandCreator to build executable assignments
     */
    override fun visit(statement: AssignStatement) {
        executableTasks.add(
            CommandCreator.instance.createCommand(
                statement.leftSide.text,
                statement.rightSide.map { it.text }, true
            )
        )
    }

    /**
     * Invokes CommandCreator to build executable commands
     */
    override fun visit(statement: CommandStatement) {
        executableTasks.add(
            CommandCreator.instance.createCommand(
                statement.command.text,
                statement.args.map { it.text }, false
            )
        )
    }
}
