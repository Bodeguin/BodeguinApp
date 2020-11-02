package pe.edu.upc.bodeguin.util

import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import pe.edu.upc.bodeguin.ui.view.home.shooping.CartAdapter
import pe.edu.upc.bodeguin.ui.viewModel.cart.CartViewModel

class ItemTouchHelperCallback(
    dragDirs: Int, swipeDirs: Int,
    private var cartAdapter: CartAdapter,
    private var cartViewModel: CartViewModel,
    private var deleteIcon: Drawable,
    private var swipeBackground: ColorDrawable
): ItemTouchHelper.SimpleCallback(dragDirs, swipeDirs) {

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false;
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        cartAdapter.removeItem(viewHolder, cartViewModel)
    }

    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        val itemView = viewHolder.itemView
        val iconMargin = (itemView.height - deleteIcon.intrinsicHeight) / 2

        if (dX < 0) {
            swipeBackground.setBounds(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
            deleteIcon.setBounds(itemView.right - iconMargin - deleteIcon.intrinsicWidth, itemView.top + iconMargin, itemView.right - iconMargin, itemView.bottom - iconMargin)
        }
        swipeBackground.draw(c)
        c.save()
        if (dX < 0) {
            c.clipRect(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
        }
        deleteIcon.draw(c)
        c.restore()

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
}