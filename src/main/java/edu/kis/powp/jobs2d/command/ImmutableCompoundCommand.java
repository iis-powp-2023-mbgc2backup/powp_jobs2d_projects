package edu.kis.powp.jobs2d.command;

import edu.kis.powp.jobs2d.Job2dDriver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ImmutableCompoundCommand implements ICompoundCommand {
	private final List<DriverCommand> commands;

	private final String name;

	private ImmutableCompoundCommand(List<DriverCommand> commands, String name) {
		List<DriverCommand> copiedCommands = new ArrayList<>();
		for (DriverCommand command : commands) {
			copiedCommands.add(command.deepCopy());
		}
		this.commands = Collections.unmodifiableList(copiedCommands);
		this.name = name;
	}

	public List<DriverCommand> getCommands() {
		return commands;
	}

	@Override
	public void execute(Job2dDriver driver) {
		commands.forEach(command -> command.execute(driver));
	}

	@Override
	public DriverCommand deepCopy() {
		return new ImmutableCompoundCommand(commands, name);
	}

	@Override
	public Iterator<DriverCommand> iterator() {
		return commands.iterator();
	}

	@Override
	public String toString() {
		return name;
	}

	public static class Builder {
		private final List<DriverCommand> commands;
		private final String name;

		public Builder(String name) {
			this.name = name;
			this.commands = new ArrayList<>();
		}

		public ImmutableCompoundCommand build() {
			return new ImmutableCompoundCommand(commands, name);
		}

		public void addCommand(DriverCommand command) {
			commands.add(command);
		}

		public void addCommands(List<DriverCommand> commands) {
			this.commands.addAll(commands);
		}

	};
  
}