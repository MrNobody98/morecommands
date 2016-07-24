package com.mrnobody.morecommands.asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

/**
 * The abstract {@link Transformer} for transformations using the visitor based
 * api of the objectweb ASM library
 *
 * @author MrNobody98
 */
public abstract class WriterTransformer extends ClassVisitor implements Transformer {
	public WriterTransformer() {
		super(Opcodes.ASM5);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final byte[] transform(String name, String transformedName, byte[] basicClass) {
		if (!this.getTransformClassNames().contains(transformedName)) return basicClass;

		MoreCommandsLoadingPlugin.logger.info("Transforming class " + transformedName);

		this.cv = new ClassWriter(this.getWriteFlags());

		this.beforeTransform();

		this.setCurrentClassName(transformedName);
		ClassReader cr = new ClassReader(basicClass);
		cr.accept(this, this.getReadFlags());

		this.afterTransform();

		return ((ClassWriter) this.cv).toByteArray();
	}

	/**
	 * Invoked before a class transformations. Sets the name of the class being transformed
	 *
	 * @param name the name of the class whose transformations begins after invoking this method
	 */
	public abstract void setCurrentClassName(String name);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getReadFlags() {
		return 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getWriteFlags() {
		return 0;
	}
}
