FROM seemongtan/kotlin-gradle 
SHELL ["/bin/bash", "-c"]
ENV TESTNAME=CountryTest
COPY . /usr/src/country
WORKDIR /usr/src/country
# CMD "source /root/.sdkman/bin/sdkman-init.sh && gradle test --test ${TESTNAME}"
# CMD cat /root/.sdkman/bin/sdkman-init.sh
CMD source /root/.sdkman/bin/sdkman-init.sh && gradle test --tests ${TESTNAME}
