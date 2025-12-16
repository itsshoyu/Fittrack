package com.fittrack.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class LatihanData {
    private static final Map<String, List<Latihan>> dataLatihan = Map.of(
        "KARDIO", List.of(
            new Latihan("Jumping Jacks", "Lompat dengan membuka kaki dan tangan ke atas, lalu kembali ke posisi semula.", "images/jumping_jaks.gif"),
            new Latihan("High Knees", "Berlari di tempat sambil mengangkat lutut setinggi mungkin ke arah dada.", "images/high_knees.gif")
        ),
        "KEKUATAN", List.of(
            new Latihan("Push-up", "Latihan untuk kekuatan dada, bahu, dan trisep.", "images/push_up.gif"),
            new Latihan("Plank", "Tahan posisi push-up dengan siku di lantai untuk melatih kekuatan inti tubuh.", "images/plank.gif"),
            new Latihan("Squat", "Gerakan jongkok untuk melatih kekuatan kaki dan bokong.", "images/squat.gif")
        ),
        "HIIT", List.of(
            new Latihan("Burpees", "Kombinasi squat, push-up, dan lompatan vertikal yang intens.", "images/burpees.gif"),
            new Latihan("Mountain Climbers", "Dari posisi plank, gerakkan lutut ke arah dada secara bergantian dengan cepat.", "images/mountain_climbers.gif")
        ),
        "KAKI", List.of(
            new Latihan("Lunges", "Langkahkan satu kaki ke depan dan tekuk kedua lutut membentuk sudut 90 derajat.", "images/lunges.gif"),
            new Latihan("Calf Raises", "Duduk atau berdiri dan angkat tumit Anda untuk melatih otot betis.", "images/calf_raises.gif")
        ),
        "FLEKSIBILITAS", List.of(
            new Latihan("Peregangan Hamstring", "Duduk dengan kaki lurus ke depan, raih ujung jari kaki Anda.", "images/peregangan_hamstring.gif"),
            new Latihan("Peregangan Bahu", "Silangkan satu tangan di depan dada dan tahan dengan tangan lainnya.", "images/peregangan_bahu.gif")
        )
    );

    public static List<Latihan> getLatihanForKategori(String kategori) {
        return dataLatihan.getOrDefault(kategori, List.of());
    }

    public static Latihan getRandomLatihan() {
        List<Latihan> semuaLatihan = new ArrayList<>();
        // Gabungkan semua latihan dari setiap kategori menjadi satu list
        for (List<Latihan> list : dataLatihan.values()) {
            semuaLatihan.addAll(list);
        }

        if (semuaLatihan.isEmpty()) {
            return new Latihan("Tidak ada Latihan", "Tambahkan data latihan terlebih dahulu.", "");
        }
        
        // Pilih satu secara acak
        Random random = new Random();
        return semuaLatihan.get(random.nextInt(semuaLatihan.size()));
    }
}