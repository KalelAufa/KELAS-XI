package com.kelas.datepicker;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView editTextDate;
    private MaterialCardView dateCard;
    private MaterialCardView resultCard;
    private MaterialCardView illustrationCard;
    private TextView selectedDateText;
    private TextView zodiacText;
    private FloatingActionButton fabClear;
    private ImageView calendarIllustration;
    private ImageView arrowIcon;
    private MaterialButton btnToday, btnTomorrow;
    private Calendar selectedCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initializeViews();
        setupClickListeners();
        startInitialAnimations();
    }

    private void initializeViews() {
        editTextDate = findViewById(R.id.editTextDate);
        dateCard = findViewById(R.id.date_card);
        resultCard = findViewById(R.id.result_card);
        illustrationCard = findViewById(R.id.illustration_card);
        selectedDateText = findViewById(R.id.selected_date_text);
        zodiacText = findViewById(R.id.zodiac_text); // pastikan TextView ini ada di layout
        fabClear = findViewById(R.id.fab_clear);
        calendarIllustration = findViewById(R.id.calendar_illustration);
        arrowIcon = findViewById(R.id.arrow_icon);
        btnToday = findViewById(R.id.btn_today);
        btnTomorrow = findViewById(R.id.btn_tomorrow);
        selectedCalendar = Calendar.getInstance();
    }

    private void setupClickListeners() {
        // Click listener untuk date card
        dateCard.setOnClickListener(v -> {
            animateCardPress(dateCard);
            animateArrowRotation();
            showDatePickerDialog();
        });

        // Click listener untuk edit text
        editTextDate.setOnClickListener(v -> {
            animateCardPress(dateCard);
            animateArrowRotation();
            showDatePickerDialog();
        });

        // Click listener untuk clear button
        fabClear.setOnClickListener(v -> clearSelectedDate());

        // Quick date buttons
        btnToday.setOnClickListener(v -> selectTodayDate());
        btnTomorrow.setOnClickListener(v -> selectTomorrowDate());

        // Illustration card animation on click
        illustrationCard.setOnClickListener(v -> {
            animateIllustration();
            showDatePickerDialog();
        });
    }

    private void startInitialAnimations() {
        // Animate illustration card entrance
        Animation bounceIn = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        illustrationCard.startAnimation(bounceIn);

        // Animate content entrance with delay
        dateCard.setAlpha(0f);
        dateCard.animate()
                .alpha(1f)
                .translationY(0f)
                .setDuration(600)
                .setStartDelay(200);

        btnToday.setAlpha(0f);
        btnToday.animate()
                .alpha(1f)
                .translationY(0f)
                .setDuration(600)
                .setStartDelay(400);

        btnTomorrow.setAlpha(0f);
        btnTomorrow.animate()
                .alpha(1f)
                .translationY(0f)
                .setDuration(600)
                .setStartDelay(500);
    }

    private void animateCardPress(View view) {
        view.animate()
                .scaleX(0.98f)
                .scaleY(0.98f)
                .setDuration(100)
                .withEndAction(() -> {
                    view.animate()
                            .scaleX(1f)
                            .scaleY(1f)
                            .setDuration(100);
                });
    }

    private void animateIllustration() {
        calendarIllustration.animate()
                .scaleX(1.2f)
                .scaleY(1.2f)
                .setDuration(200)
                .withEndAction(() -> {
                    calendarIllustration.animate()
                            .scaleX(1f)
                            .scaleY(1f)
                            .setDuration(200);
                });
    }

    private void animateArrowRotation() {
        arrowIcon.animate()
                .rotation(180f)
                .setDuration(300)
                .withEndAction(() -> {
                    arrowIcon.animate()
                            .rotation(0f)
                            .setDuration(300);
                });
    }

    private void showDatePickerDialog() {
        final Calendar currentDate = Calendar.getInstance();
        int year = currentDate.get(Calendar.YEAR);
        int month = currentDate.get(Calendar.MONTH);
        int day = currentDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
            this,
            (view, selectedYear, selectedMonth, selectedDay) -> {
                selectedCalendar.set(selectedYear, selectedMonth, selectedDay);
                updateDateDisplay(selectedYear, selectedMonth, selectedDay);
                showResultCard();
                showClearButton();
                showSuccessMessage();
            },
            year, month, day
        );

        datePickerDialog.show();
    }

    private void selectTodayDate() {
        Calendar today = Calendar.getInstance();
        selectedCalendar = (Calendar) today.clone();
        updateDateDisplay(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH));
        showResultCard();
        showClearButton();
        animateButtonPress(btnToday);
        showSuccessMessage();
    }

    private void selectTomorrowDate() {
        Calendar tomorrow = Calendar.getInstance();
        tomorrow.add(Calendar.DAY_OF_MONTH, 1);
        selectedCalendar = (Calendar) tomorrow.clone();
        updateDateDisplay(tomorrow.get(Calendar.YEAR), tomorrow.get(Calendar.MONTH), tomorrow.get(Calendar.DAY_OF_MONTH));
        showResultCard();
        showClearButton();
        animateButtonPress(btnTomorrow);
        showSuccessMessage();
    }

    private void animateButtonPress(MaterialButton button) {
        button.animate()
                .scaleX(0.95f)
                .scaleY(0.95f)
                .setDuration(100)
                .withEndAction(() -> {
                    button.animate()
                            .scaleX(1f)
                            .scaleY(1f)
                            .setDuration(100);
                });
    }

    private String getZodiac(int day, int month) {
        month += 1; // Calendar.MONTH dimulai dari 0
        if (month == 1) return day < 20 ? "Capricorn" : "Aquarius";
        if (month == 2) return day < 19 ? "Aquarius" : "Pisces";
        if (month == 3) return day < 21 ? "Pisces" : "Aries";
        if (month == 4) return day < 20 ? "Aries" : "Taurus";
        if (month == 5) return day < 21 ? "Taurus" : "Gemini";
        if (month == 6) return day < 21 ? "Gemini" : "Cancer";
        if (month == 7) return day < 23 ? "Cancer" : "Leo";
        if (month == 8) return day < 23 ? "Leo" : "Virgo";
        if (month == 9) return day < 23 ? "Virgo" : "Libra";
        if (month == 10) return day < 23 ? "Libra" : "Scorpio";
        if (month == 11) return day < 22 ? "Scorpio" : "Sagittarius";
        if (month == 12) return day < 22 ? "Sagittarius" : "Capricorn";
        return "Unknown";
    }

    private void updateDateDisplay(int year, int month, int day) {
        // Format tanggal untuk display
        String displayDate = String.format(Locale.getDefault(), 
            "%02d/%02d/%d", day, month + 1, year);
        editTextDate.setText(displayDate);

        // Format tanggal yang lebih readable untuk result card
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd MMMM yyyy", 
            new Locale("id", "ID"));
        Date date = selectedCalendar.getTime();
        String readableDate = sdf.format(date);
        selectedDateText.setText(readableDate);
        // Tampilkan zodiak
        String zodiac = getZodiac(day, month);
        zodiacText.setText("Zodiak: " + zodiac);
    }

    private void showResultCard() {
        if (resultCard.getVisibility() == View.GONE) {
            resultCard.setVisibility(View.VISIBLE);
            resultCard.setAlpha(0f);
            resultCard.setTranslationY(50f);
            resultCard.animate()
                    .alpha(1f)
                    .translationY(0f)
                    .setDuration(400);
        }
    }

    private void showClearButton() {
        if (fabClear.getVisibility() == View.GONE) {
            fabClear.setVisibility(View.VISIBLE);
            fabClear.setScaleX(0f);
            fabClear.setScaleY(0f);
            fabClear.animate()
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(300);
        }
    }

    private void clearSelectedDate() {
        editTextDate.setText("Belum dipilih");
        
        // Hide result card with animation
        resultCard.animate()
                .alpha(0f)
                .translationY(-50f)
                .setDuration(300)
                .withEndAction(() -> resultCard.setVisibility(View.GONE));

        // Hide clear button
        fabClear.animate()
                .scaleX(0f)
                .scaleY(0f)
                .setDuration(200)
                .withEndAction(() -> fabClear.setVisibility(View.GONE));

        Snackbar.make(findViewById(R.id.main), "Tanggal telah dihapus", Snackbar.LENGTH_SHORT)
                .setAction("UNDO", v -> {
                    // Implement undo functionality if needed
                })
                .show();
    }

    private void showSuccessMessage() {
        Snackbar.make(findViewById(R.id.main), "Tanggal berhasil dipilih!", Snackbar.LENGTH_SHORT)
                .setBackgroundTint(getResources().getColor(R.color.success_color))
                .show();
    }
}