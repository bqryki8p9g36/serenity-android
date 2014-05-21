/**
 * The MIT License (MIT)
 * Copyright (c) 2012 David Carver
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 * OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF
 * OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package us.nineworlds.serenity.volley;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import com.android.volley.RequestQueue;

@RunWith(RobolectricTestRunner.class)
@Config(reportSdk = 14, emulateSdk = 18)
public class VolleyUtilsTest {

	@Test
	public void assertThatVolleyRequestQueueIsNotNull() {
		RequestQueue queue = VolleyUtils
				.getRequestQueueInstance(Robolectric.application);
		assertThat(queue).isNotNull();
	}

	@Test
	public void assertThatVolleyRequestQueueIsSameInstance() {
		RequestQueue queue = VolleyUtils
				.getRequestQueueInstance(Robolectric.application);
		RequestQueue queue2 = VolleyUtils
				.getRequestQueueInstance(Robolectric.application);
		assertThat(queue).isEqualTo(queue2);

	}
}
