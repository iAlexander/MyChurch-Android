package com.d2.pcu.utils.exo;

import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.extractor.mp3.Mp3Extractor;

public class Mp3ExtractorsFactory implements ExtractorsFactory {
    @Override
    public Extractor[] createExtractors() {
        return new Extractor[] {new Mp3Extractor()};
    }
}