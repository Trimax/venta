package com.gesoftware.venta.math.vectors;

import java.io.Serializable;

/**
 * Vec2i class definition
 */
public final class Vec2i implements Serializable {
    private int m_X;
    private int m_Y;

    /* *
     * METHOD: Vec2i class constructor
     *  PARAM: [IN] x - abscissa component
     *  PARAM: [IN] y - ordinate component
     * AUTHOR: Eliseev Dmitry
     * */
    public Vec2i(final int x, final int y) {
        this.m_X = x;
        this.m_Y = y;
    } /* End of 'Vec2i::Vec2i' method */

    /* *
     * METHOD: Abscissa getter
     * RETURN: Abscissa value
     * AUTHOR: Eliseev Dmitry
     * */
    public final int getX() {
        return m_X;
    } /* End of 'Vec2i::getX' method */

    /* *
     * METHOD: Abscissa getter
     * RETURN: Ordinate value
     * AUTHOR: Eliseev Dmitry
     * */
    public final int getY() {
        return m_Y;
    } /* End of 'Vec2i::getY' method */

    /* *
     * METHOD: Abscissa setter
     *  PARAM: [IN] x - new abscissa value
     * AUTHOR: Eliseev Dmitry
     * */
    public final void setX(final int x) {
        m_X = x;
    } /* End of 'Vec2i::setX' method */

    /* *
     * METHOD: Ordinate setter
     *  PARAM: [IN] y - new ordinate value
     * AUTHOR: Eliseev Dmitry
     * */
    public final void setY(final int y) {
        m_Y = y;
    } /* End of 'Vec2i::setY' method */

    /* *
     * METHOD: Components setter
     *  PARAM: [IN] x - new abscissa value
     *  PARAM: [IN] y - new ordinate value
     * AUTHOR: Eliseev Dmitry
     * */
    public final void set(final int x, final int y) {
        m_X = x;
        m_Y = y;
    } /* End of 'Vec2i::set' method */

    /* *
     * METHOD: Components setter
     *  PARAM: [IN] v - new value
     * AUTHOR: Eliseev Dmitry
     * */
    public final void set(final Vec2i v) {
        m_X = v.m_X;
        m_Y = v.m_Y;
    } /* End of 'Vec2i::set' method */

    /* *
     * METHOD: Converts integer vector to a real
     * RETURN: Real vector
     * AUTHOR: Eliseev Dmitry
     * */
    public final Vec2r toVec2r() {
        return new Vec2r(m_X, m_Y);
    } /* End of 'Vec2i::toVec2r' method */

    @Override
    public final boolean equals(final Object o) {
        return o instanceof Vec2i && ((Vec2i) o).getX() == getX() && ((Vec2i) o).getY() == getY();
    } /* End of 'Vec2i::equals' method */

    @Override
    public final String toString() {
        return "[" + m_X + ", " + m_Y + "]";
    } /* End of 'Vec2i::toString' method */
} /* End of 'Vec2i' class */