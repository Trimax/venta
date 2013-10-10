package com.gesoftware.venta.math.vectors;

import com.gesoftware.venta.math.tools.Mathematics;

import java.io.Serializable;

/**
 * Vec2i class definition
 */
public final class Vec2r implements Serializable {
    private double m_X;
    private double m_Y;

    /* *
     * METHOD: Vec2r class constructor
     *  PARAM: [IN] x - abscissa component
     *  PARAM: [IN] y - ordinate component
     * AUTHOR: Eliseev Dmitry
     * */
    public Vec2r() {
        m_X = 0.0;
        m_Y = 0.0;
    } /* End of 'Vec2r::Vec2r' method */

    /* *
     * METHOD: Vec2r class constructor
     *  PARAM: [IN] x - abscissa component
     *  PARAM: [IN] y - ordinate component
     * AUTHOR: Eliseev Dmitry
     * */
    public Vec2r(final double x, final double y) {
        m_X = x;
        m_Y = y;
    } /* End of 'Vec2r::Vec2r' method */

    /* *
     * METHOD: Adds two vectors using per-component sum
     * RETURN: Per-component vectors sum
     *  PARAM: [IN] v - vector to add
     * AUTHOR: Eliseev Dmitry
     * */
    public final Vec2r add(final Vec2r v) {
        return new Vec2r(m_X + v.m_X, m_Y + v.m_Y);
    } /* End of 'Vec2r::add' method */

    /* *
     * METHOD: Adds a vector to self
     *  PARAM: [IN] v - vector to add
     * AUTHOR: Eliseev Dmitry
     * */
    public final void addSelf(final Vec2r v) {
        m_X += v.getX();
        m_Y += v.getY();
    } /* End of 'Vec2r::addSelf' method */

    /* *
     * METHOD: Diffs two vectors using per-component difference
     * RETURN: Per-component vectors difference
     *  PARAM: [IN] v - vector to diff
     * AUTHOR: Eliseev Dmitry
     * */
    public final Vec2r diff(final Vec2r v) {
        return new Vec2r(m_X - v.m_X, m_Y - v.m_Y);
    } /* End of 'Vec2r::diff' method */

    /* *
   * METHOD: Diffs a vector from self
   *  PARAM: [IN] v - vector to diff
   * AUTHOR: Eliseev Dmitry
   * */
    public final void diffSelf(final Vec2r v) {
        m_X -= v.getX();
        m_Y -= v.getY();
    } /* End of 'Vec2r::diffSelf' method */

    /* *
     * METHOD: Multiplies vector with a number
     * RETURN: Vector, multiplied with a number
     *  PARAM: [IN] a - multiplier
     * AUTHOR: Eliseev Dmitry
     * */
    public final Vec2r multiply(final double a) {
        return new Vec2r(m_X * a, m_Y * a);
    } /* End of 'Vec2r::multiply' method */

    /* *
     * METHOD: Multiplies self with a number
     *  PARAM: [IN] a - multiplier
     * AUTHOR: Eliseev Dmitry
     * */
    public final void multiplySelf(final double a) {
        m_X *= a;
        m_Y *= a;
    } /* End of 'Vec2r::multiplySelf' method */

    /* *
     * METHOD: Abscissa getter
     * RETURN: Abscissa value
     * AUTHOR: Eliseev Dmitry
     * */
    public final double getX() {
        return m_X;
    } /* End of 'Vec2r::getX' method */

    /* *
     * METHOD: Ordinate getter
     * RETURN: Ordinate value
     * AUTHOR: Eliseev Dmitry
     * */
    public final double getY() {
        return m_Y;
    } /* End of 'Vec2r::getY' method */

    /* *
     * METHOD: Abscissa setter
     *  PARAM: [IN] x - new abscissa value
     * AUTHOR: Eliseev Dmitry
     * */
    public final void setX(final double x) {
        m_X = x;
    } /* End of 'Vec2r::setX' method */

    /* *
     * METHOD: Ordinate setter
     *  PARAM: [IN] y - new ordinate value
     * AUTHOR: Eliseev Dmitry
     * */
    public final void setY(final double y) {
        m_Y = y;
    } /* End of 'Vec2r::setY' method */

    /* *
     * METHOD: Components setter
     *  PARAM: [IN] x - new abscissa value
     *  PARAM: [IN] y - new ordinate value
     * AUTHOR: Eliseev Dmitry
     * */
    public final void set(final double x, final double y) {
        m_X = x;
        m_Y = y;
    } /* End of 'Vec2r::set' method */

    /* *
     * METHOD: Components setter
     *  PARAM: [IN] v - value to set
     * AUTHOR: Eliseev Dmitry
     * */
    public final void set(final Vec2r v) {
        m_X = v.m_X;
        m_Y = v.m_Y;
    } /* End of 'Vec2r::set' method */

    @Override
    public final boolean equals(final Object obj) {
        return obj instanceof Vec2r && Mathematics.equals(((Vec2r) obj).getX(), m_X) && Mathematics.equals(((Vec2r) obj).getY(), m_Y);
    } /* End of 'Vec2r::equals' method */

    @Override
    public final String toString() {
        return "[" + m_X + "; " + m_Y + "]";
    } /* End of 'Vec2r::toString' method */
} /* End of 'Vec2r' class */