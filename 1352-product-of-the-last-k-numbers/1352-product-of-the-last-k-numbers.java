import java.util.ArrayList;
import java.util.List;

class ProductOfNumbers {
    private List<Integer> prefixProducts;

    public ProductOfNumbers() {
        prefixProducts = new ArrayList<>();
        // Initialize with 1 as a neutral base for multiplication
        prefixProducts.add(1);
    }
    
    public void add(int num) {
        if (num == 0) {
            // A zero invalidates all previous products, so reset the list
            prefixProducts = new ArrayList<>();
            prefixProducts.add(1);
        } else {
            // Multiply the new number by the last cumulative product
            int lastProduct = prefixProducts.get(prefixProducts.size() - 1);
            prefixProducts.add(lastProduct * num);
        }
    }
    
    public int getProduct(int k) {
        int n = prefixProducts.size();
        // If k reaches back to or past the last zero, the product is 0
        if (k >= n) {
            return 0;
        }
        // Divide the latest cumulative product by the product right before the last k elements
        return prefixProducts.get(n - 1) / prefixProducts.get(n - 1 - k);
    }
}
